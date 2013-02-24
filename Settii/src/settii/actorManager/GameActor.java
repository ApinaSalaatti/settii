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
    
    private String resource; // the xml-file this actor was created from
    
    public GameActor(long id, String res) {
        actorID = id;
        components = new HashMap<String, BaseComponent>();
        resource = res;
    }
    
    public long getID() {
        return actorID;
    }
    
    public void addComponent(BaseComponent comp) {
        components.put(comp.getName(), comp);
    }
    public BaseComponent getComponent(String comp) {
        return components.get(comp);
    }
    
    public String getResource() {
        return resource;
    }
    
    public void move(float x, float y) {
        PhysicsComponent pc = (PhysicsComponent)getComponent("PhysicsComponent");
        
        if(pc != null) {
            pc.setLocation(x, y);
        }
    }
    
    public void update(long deltaMs) {
        for(BaseComponent b : components.values()) {
            b.update(deltaMs);
        }
    }
    
    public boolean locationWithinActor(double x, double y) {
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
    }
}
