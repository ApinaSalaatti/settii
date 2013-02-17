package settii.actorManager.components;

import settii.actorManager.BaseComponent;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author Merioksan Mikko
 */
public class StatusComponent extends BaseComponent {
    public static final long NO_PARENT = -1;
    
    private String actorType; // what kind of actor are we, i.e. projectile, building, cannon, etc
    private String allegiance; // whose side are we on
    private long parent; // this actors parent-actor's id. -1 if no parent specified
    
    public StatusComponent() {
        parent = NO_PARENT;
    }
    @Override
    public String getName() {
        return "StatusComponent";
    }
    
    public String getType() {
        return actorType;
    }
    public void setType(String type) {
        actorType = type;
    }
    
    public String getAlleciange() {
        return allegiance;
    }
    public void setAllegiance(String a) {
        allegiance = a;
    }
    
    public long getParent() {
        return parent;
    }
    public void setParent(long id) {
        parent = id;
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        for(int i = 0; i < attributes.getLength(); i++) {
            Node node = attributes.item(i);
            
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Node value = node.getFirstChild();
                if(node.getNodeName().equalsIgnoreCase("ActorType")) {
                    actorType = value.getNodeValue();
                }
                else if(node.getNodeName().equalsIgnoreCase("Allegiance")) {
                    allegiance = value.getNodeValue();
                }
            }
        }
    }
}