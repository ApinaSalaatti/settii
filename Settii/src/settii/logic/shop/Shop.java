package settii.logic.shop;

import java.util.HashMap;
import java.util.Collection;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.*;
import settii.eventManager.events.AttemptRepairEvent;
import settii.eventManager.events.AttemptToBuyEvent;
import settii.eventManager.events.researchEvents.NewShopItemEvent;
import settii.eventManager.events.shopEvents.*;
import settii.logic.mouse.PlaceWeaponAction;
/**
 *
 * @author Merioksan Mikko
 */
public class Shop {
    private HashMap<String, ShopItem> availableItems;
    private HashMap<String, ShopItem> allItems;
    
    // special "item" that repairs the base.
    private int baseRepairCost;
    
    public Shop() {
        availableItems = new HashMap<String, ShopItem>();
        allItems = new HashMap<String, ShopItem>();
        
        Application.get().getEventManager().register(AttemptToBuyEvent.eventType, new AttemptToBuyListener(this));
        Application.get().getEventManager().register(AttemptRepairEvent.eventType, new AttemptRepairListener(this));
        Application.get().getEventManager().register(BuyActorEvent.eventType, new BuyActorListener(this));
        Application.get().getEventManager().register(NewShopItemEvent.eventType, new NewShopItemListener(this));
    }
    
    public void makeAvailable(String key) {
        if(allItems.containsKey(key)) {
            availableItems.put(key, allItems.get(key));
        }
    }
    
    public Collection<ShopItem> getAvailableItems() {
        return availableItems.values();
    }
    public HashMap<String, ShopItem> getAllItems() {
        return allItems;
    }
    
    public int getBaseRepairCost() {
        return baseRepairCost;
    }
    
    public void createFromXML(NodeList shop) {
        Node n = shop.item(0);
        while(n != null) {
            if(n.getNodeType() == Node.ELEMENT_NODE) {
                if(n.getNodeName().equalsIgnoreCase("ShopItem")) {
                    createShopItem(n);
                }
                else if(n.getNodeName().equalsIgnoreCase("AvailableItem")) {
                    makeAvailable(n.getFirstChild().getNodeValue());
                }
                else if(n.getNodeName().equalsIgnoreCase("InitialRepairCost")) {
                    baseRepairCost = Integer.parseInt(n.getFirstChild().getNodeValue());
                }
            }
            
            n = n.getNextSibling();
        }
    }
    
    public void createShopItem(Node item) {
        String name = null;
        String description = null;
        String sprite = null;
        int cost = 0;
        ShopEvent event = null;
        
        Node attr = item.getFirstChild();
        while(attr != null) {
            if(attr.getNodeType() == Node.ELEMENT_NODE) {
                if(attr.getNodeName().equalsIgnoreCase("Name")) {
                    name = attr.getFirstChild().getNodeValue();
                }
                else if(attr.getNodeName().equalsIgnoreCase("Description")) {
                    description = attr.getFirstChild().getNodeValue();
                }
                else if(attr.getNodeName().equalsIgnoreCase("Event")) {
                    event = createEvent(attr, cost); // remember to put cost-node before the event-node in the xml. :--------)
                }
                else if(attr.getNodeName().equalsIgnoreCase("Sprite")) {
                    sprite = attr.getFirstChild().getNodeValue();
                }
                else if(attr.getNodeName().equalsIgnoreCase("Cost")) {
                    cost = Integer.parseInt(attr.getFirstChild().getNodeValue());
                }
            }
            attr = attr.getNextSibling();
        }
        
        allItems.put(event.getResource(), new ShopItem(name, description, sprite, cost, event));
    }
    
    public ShopEvent createEvent(Node n, int value) {
        String name = "";
        String res = "";
        
        Node attr = n.getFirstChild();
        while(attr != null) {
            if(attr.getNodeType() == Node.ELEMENT_NODE) {
                if(attr.getNodeName().equalsIgnoreCase("Name")) {
                    name = attr.getFirstChild().getNodeValue();
                }
                else if(attr.getNodeName().equalsIgnoreCase("Resource")) {
                    res = attr.getFirstChild().getNodeValue();
                }
            }
            attr = attr.getNextSibling();
        }
        
        if(name.equalsIgnoreCase("Buy Actor")) {
            return new BuyActorEvent(value, res);
        }
        
        return null;
    }
    
    // This works for research and shop items. Good or bad????
    public void attemptToBuyListener(Sellable item) {
        InventoryComponent inv = Application.get().getLogic().getGame().getPlayer().getInventory();
        if(inv.getMoney() >= item.getValue()) {
            inv.removeMoney(item.getValue());
            
            // if something went wrong when buying, return the money.
            if(!item.buy()) {
                inv.addMoney(item.getValue());
            }
        }
    }
    
    public void attemptRepairListener() {
        InventoryComponent inv = Application.get().getLogic().getGame().getPlayer().getInventory();
        if(inv.getMoney() >= baseRepairCost) {
            Application.get().getEventManager().queueEvent(new RepairBaseEvent(baseRepairCost));
            inv.removeMoney(baseRepairCost);
            baseRepairCost *= 2;
        }
    }
    
    // TODO: should this be here?!!
    public void buyActorListener(String resource, int value) {
        GameActor toBuy = Application.get().getLogic().createActor(resource);

        Application.get().getLogic().getGame().setCurrentMouseAction(new PlaceWeaponAction(toBuy, value));
    }
    
    public void newShopItemListener(String resource) {
        makeAvailable(resource);
    }
}
