package settii.logic.shop;

import settii.eventManager.events.researchEvents.UpdateDamageEvent;
import java.util.HashMap;
import java.util.Collection;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.BaseComponent;
import settii.actorManager.components.*;
import settii.eventManager.events.AttemptToBuyEvent;
import settii.eventManager.events.shopEvents.*;
import settii.logic.Player;
import settii.logic.mouse.PlaceWeaponAction;
/**
 *
 * @author Merioksan Mikko
 */
public class Shop {
    // items
    public static String CANNON = "assets/data/actors/cannon.xml";
    public static int CANNON_DEFAULT_VALUE = 100;
    public static String BIG_CANNON = "assets/data/actors/bigCannon.xml";
    public static int BIG_CANNON_DEFAULT_VALUE = 500;
    
    private HashMap<String, ShopItem> availableItems;
    private HashMap<String, ShopItem> allItems;
    
    public Shop() {
        availableItems = new HashMap<String, ShopItem>();
        allItems = new HashMap<String, ShopItem>();
        createShop();
        
        Application.get().getEventManager().register(AttemptToBuyEvent.eventType, new AttemptToBuyListener(this));
        Application.get().getEventManager().register(BuyActorEvent.eventType, new BuyActorListener(this));
    }
    
    private void createShop() {
        allItems.put(CANNON, new ShopItem("assets/graphics/shop/cannon.png", CANNON_DEFAULT_VALUE, new BuyActorEvent(CANNON_DEFAULT_VALUE, "assets/data/actors/cannon.xml")));
        allItems.put(BIG_CANNON, new ShopItem("assets/graphics/shop/bigCannon.png", BIG_CANNON_DEFAULT_VALUE, new BuyActorEvent(BIG_CANNON_DEFAULT_VALUE, "assets/data/actors/bigCannon.xml")));
        
        makeAvailable(CANNON);
        makeAvailable(BIG_CANNON);
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
    
    // This works for research and shop items. Good or bad????
    public void attemptToBuyListener(Sellable item) {
        InventoryComponent inv = Application.get().getLogic().getGame().getPlayer().getInventory();
        if(inv.getMoney() >= item.getValue()) {
            inv.removeMoney(item.getValue());
            item.buy();
        }
    }
    
    // TODO: should this be here?!!
    public void buyActorListener(String resource, int value) {
        GameActor toBuy = Application.get().getLogic().getActor(Application.get().getLogic().createActor(resource));

        Application.get().getLogic().getGame().setCurrentMouseAction(new PlaceWeaponAction(toBuy, value));
    }
}
