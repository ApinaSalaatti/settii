package settii.actorManager;

import java.awt.Rectangle;
import java.lang.reflect.Constructor;
import settii.actorManager.components.*;
import settii.Application;
import settii.eventManager.events.ActorMovedEvent;
import java.util.HashMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author Merioksan Mikko
 */
public class GameActor {
    private long actorID;
    private HashMap<String, BaseComponent> components;
    private HashMap<String, Boolean> enabledComponents;
    
    private String resource; // the xml-file this actor was created from
    
    private boolean enabled; // is the actor enabled, i.e. correctly placed in the world and stuff (this is propably really stupid thing...)
    
    public GameActor(long id, String res) {
        actorID = id;
        components = new HashMap<String, BaseComponent>();
        enabledComponents = new HashMap<String, Boolean>();
        resource = res;
        enabled = true;
    }
    
    public long getID() {
        return actorID;
    }
    
    public void addComponent(BaseComponent comp) {
        components.put(comp.getName(), comp);
        enabledComponents.put(comp.getName().toLowerCase(), true);
    }
    public BaseComponent getComponent(String comp) {
        return components.get(comp);
    }
    
    public String getResource() {
        return resource;
    }
    
    public void disable() {
        enabled = false;
    }
    public void enable() {
        enabled = true;
    }
    public boolean isEnabled() {
        return enabled;
    }
    
    public void enableComponent(String comp) {
        comp = comp.toLowerCase();
        enabledComponents.put(comp, true);
    }
    public void disableComponent(String comp) {
        comp = comp.toLowerCase();
        enabledComponents.put(comp, false);
    }
    
    public void move(float x, float y) {
        PhysicsComponent pc = (PhysicsComponent)getComponent("PhysicsComponent");
        
        if(pc != null) {
            pc.setLocation(x, y);
        }
    }
    
    public void update(long deltaMs) {
        if(enabled) { // don't update disabled actors
            for(BaseComponent b : components.values()) {
                if(enabledComponents.get(b.getName().toLowerCase())) {
                    b.update(deltaMs);
                }
            }
        }
    }
    
    public boolean locationWithinActor(double x, double y) {
        if(enabled) { // disabled actors cannot be targeted (TODO: is this good???)
            PhysicsComponent pc = (PhysicsComponent)getComponent("PhysicsComponent");
            //HitboxComponent hc = (HitboxComponent)getComponent("HitboxComponent");

            if(pc != null) {
                Rectangle hb = pc.getHitbox();
                if(x > hb.getX() && x < hb.getX() + hb.getWidth() && y > hb.getY() && y < hb.getY() + hb.getHeight()) {
                    return true;
                }
            }

            // TODO: add proper hitboxes perhaps maybe
            /*
            if(lc != null && hc != null) {
                double relativeX = x - lc.getX();
                double relativeY = y - lc.getY();

                return hc.locationInsideHitbox(relativeX, relativeY);
            }
            */
        }
        return false;
    }
    
    public void createFromXML(NodeList components) {
        try {
            for(int i = 0; i < components.getLength(); i++) {
                Node node = components.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Class c = Class.forName("settii.actorManager.components." + node.getNodeName());
                    Constructor constr = c.getConstructor();
                    BaseComponent bc = (BaseComponent)constr.newInstance();
                    bc.setOwner(this);
                    if(node.getChildNodes() != null && node.getChildNodes().getLength() > 0)  {
                        bc.createFromXML(node.getChildNodes());
                    }
                    
                    this.addComponent(bc);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void destroy() {
        for(BaseComponent bc : components.values()) {
            bc.destroy();
        }
        components.clear();
    }
    
    /**
     * Copies this actors components to another actor
     * @param a actor to copy components to
     */
    public void copyTo(GameActor a) {
        for(BaseComponent bc : components.values()) {
            BaseComponent bc2 = a.getComponent(bc.getName());
            if(bc2 != null) {
                bc.copyTo(bc2);
            }
        }
        
        for(String comp : enabledComponents.keySet()) {
            if(enabledComponents.get(comp)) {
                a.enableComponent(comp);
            }
            else {
                a.disableComponent(comp);
            }
        }
    }
}
