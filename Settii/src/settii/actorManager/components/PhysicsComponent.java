package settii.actorManager.components;

import java.util.HashMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import settii.actorManager.BaseComponent;
import settii.eventManager.EventManager;
import settii.eventManager.events.*;
import settii.views.humanView.RenderObject;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class PhysicsComponent extends BaseComponent {
    private float x, y;
    private float width, height;
    
    public PhysicsComponent() {
        
    }
    
    public PhysicsComponent(long owner) {
        super.setOwner(owner);
    }
    
    @Override
    public String getName() {
        return "PhysicsComponent";
    }
    
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public void setLocation(float x, float y) {
        this.x = x - width / 2; // location always at the center of the object
        this.y = y - height / 2;
    }
    
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        for(int i = 0; i < attributes.getLength(); i++) {
            Node node = attributes.item(i);
            
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Node value = node.getFirstChild();
                if(node.getNodeName().equalsIgnoreCase("y")) {
                    y = Float.parseFloat(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("x")) {
                    x = Float.parseFloat(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("width")) {
                    width = Float.parseFloat(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("height")) {
                    height = Float.parseFloat(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("rendering")) {
                    HashMap<String, String> files = new HashMap<String, String>();
                    while(value != null) {
                        files.put(value.getNodeName(), value.getFirstChild().getNodeValue());
                        value = value.getNextSibling();
                    }
                    Application.get().getEventManager().queueEvent(new RenderableActorCreatedEvent(new RenderObject(owner, x, y, files)));
                }
            }
        }
    }
}
