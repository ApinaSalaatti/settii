package settii.logic.shop;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import settii.Application;
import settii.views.humanView.renderer.Texture;
import settii.eventManager.events.shopEvents.*;
/**
 *
 * @author Merioksan Mikko
 */
public class ShopItem implements Sellable {
    private String resource;
    private String sprite;
    private int damage;
    private int health;
    
    private ShopEvent event;
    
    private int value;
    
    public ShopItem(String sprite, int value, ShopEvent event) {
        this.sprite = sprite;
        this.value = value;
        this.event = event;
        //getData(res);
    }
    /*
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
    * 
    public String getResource() {
        return resource;
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
    
    */
    
    public String getSprite() {
        return sprite;
    }
    
    @Override
    public int getValue() {
        return value;
    }
    public void setValue(int v){
        value = v;
    }
    
    @Override
    public void buy() {
        Application.get().getEventManager().queueEvent(event);
    }
}
