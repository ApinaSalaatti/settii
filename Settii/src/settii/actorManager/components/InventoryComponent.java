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
    private int crap;
    private int cum;
    
    public InventoryComponent() {
        money = 0;
        crap = 0;
        cum = 0;
    }
    
    @Override
    public String getName() {
        return "InventoryComponent";
    }
    
    public int getMoney() {
        return money;
    }
    public void setMoney(int m) {
        money = m;
    }
    public void addMoney(int amount) {
        money += amount;
    }
    public void removeMoney(int amount) {
        money -= amount;
    }
    
    public int getCrap() {
        return crap;
    }
    public void setCrap(int c) {
        crap = c;
    }
    public void addCrap(int amount) {
        crap += amount;
    }
    public void removeCrap(int amount) {
        crap -= amount;
    }
    
    public int getCum() {
        return cum;
    }
    public void setCum(int c) {
        cum = c;
    }
    public void addCum(int amount) {
        cum += amount;
    }
    public void removeCum(int amount) {
        cum -= amount;
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
                if(node.getNodeName().equalsIgnoreCase("crap")) {
                    crap = Integer.parseInt(value.getNodeValue());
                }
                if(node.getNodeName().equalsIgnoreCase("cum")) {
                    cum = Integer.parseInt(value.getNodeValue());
                }
            }
        }
    }
    
    @Override
    public void copyTo(BaseComponent bc) {
        InventoryComponent ic = (InventoryComponent)bc;
        ic.setMoney(money);
        ic.setCrap(crap);
        ic.setCum(cum);
    }
}
