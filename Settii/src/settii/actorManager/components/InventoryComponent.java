/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.actorManager.components;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import settii.actorManager.BaseComponent;
/**
 *
 * @author Merioksan Mikko
 */
public class InventoryComponent extends BaseComponent {
    private int money;
    
    public InventoryComponent() {
        money = 0;
    }
    
    @Override
    public String getName() {
        return "InventoryComponent";
    }
    
    public int getMoney() {
        return money;
    }
    public void addMoney(int amount) {
        money += amount;
    }
    
    @Override
    public void update(long deltaMs) {
        
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        for(int i = 0; i < attributes.getLength(); i++) {
            Node node = attributes.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Node value = node.getFirstChild();
                if(node.getNodeName().equalsIgnoreCase("money")) {
                    money = Integer.parseInt(value.getNodeValue());
                }
            }
        }
    }
}
