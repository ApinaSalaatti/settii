package settii.logic.shop;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import settii.Application;
import settii.views.humanView.renderer.Texture;
/**
 *
 * @author Merioksan Mikko
 */
public class ShopItem {
    private String resource;
    private String sprite;
    private int damage;
    private int health;
    
    private int value;
    
    public ShopItem(String res) {
        resource = res;
        getData(res);
    }
    
    private void getData(String res) {
        NodeList components = Application.get().getResourceManager().getDataManager().getData(res);
        for(int i = 0; i < components.getLength(); i++) {
            Node node = components.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                if(node.getNodeName().equalsIgnoreCase("PhysicsComponent")) {
                    Node child = node.getFirstChild();
                    while(child != null) {
                        if(child.getNodeName().equalsIgnoreCase("health")) {
                            Node value = child.getFirstChild();
                            health = Integer.parseInt(value.getNodeValue());
                        }
                        else if(child.getNodeName().equalsIgnoreCase("rendering")) {
                            Node child2 = child.getFirstChild();
                            while(child2 != null) {
                                if(child2.getNodeName().equalsIgnoreCase("default")) {
                                    Node value = child2.getFirstChild();
                                    sprite = value.getNodeValue();
                                }
                                child2 = child2.getNextSibling();
                            }
                        }
                        child = child.getNextSibling();
                    }
                }
                else if(node.getNodeName().equalsIgnoreCase("WeaponsComponent")) {
                    Node child = node.getFirstChild();
                    while(child != null) {
                        if(child.getNodeName().equalsIgnoreCase("damage")) {
                            Node value = child.getFirstChild();
                            damage = Integer.parseInt(value.getNodeValue());
                        }
                        child = child.getNextSibling();
                    }
                }
            }
        }
    }
    
    public String getResource() {
        return resource;
    }
    
    public String getSprite() {
        return sprite;
    }
    
    public int getDamage() {
        return damage;
    }
    public void setDamage(int d) {
        damage = d;
    }
    
    public int getHealth() {
        return health;
    }
    public void setHealth(int h) {
        health = h;
    }
    
    public int getValue() {
        return value;
    }
    public void setValue(int v){
        value = v;
    }
}
