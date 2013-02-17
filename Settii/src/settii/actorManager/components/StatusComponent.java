package settii.actorManager.components;

import settii.actorManager.BaseComponent;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author Merioksan Mikko
 */
public class StatusComponent extends BaseComponent {
    private String actorType;
    private String allegiance;
    
    public StatusComponent() {
        
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
