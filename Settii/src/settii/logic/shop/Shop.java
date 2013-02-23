package settii.logic.shop;

import java.util.HashMap;
import java.util.Collection;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.BaseComponent;
import settii.actorManager.components.*;
import settii.eventManager.events.AttemptToBuyEvent;
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
    }
    
    private void createShop() {
        allItems.put(CANNON, new ShopItem(CANNON));
        allItems.get(CANNON).setValue(CANNON_DEFAULT_VALUE);
        
        allItems.put(BIG_CANNON, new ShopItem(BIG_CANNON));
        allItems.get(BIG_CANNON).setValue(BIG_CANNON_DEFAULT_VALUE);
        
        makeAvailable(CANNON);
        makeAvailable(BIG_CANNON);
    }
    
    public void makeAvailable(String key) {
        if(allItems.containsKey(key)) {
            availableItems.put(key, allItems.get(key));
        }
    }
    
    public ShopItem getActor(String key) {
        return allItems.get(key);
    }
    
    public Collection<ShopItem> getAvailableItems() {
        return availableItems.values();
    }
    public HashMap<String, ShopItem> getAllItems() {
        return allItems;
    }
    
    public void attemptToBuyListener(ShopItem item) {
        InventoryComponent inv = Application.get().getLogic().getGame().getPlayer().getInventory();
        
        if(inv.getMoney() >= item.getValue()) {
            GameActor toBuy = Application.get().getLogic().getActor(Application.get().getLogic().createActor(item.getResource()));

            WeaponsComponent wc = (WeaponsComponent)toBuy.getComponent("WeaponsComponent");
            wc.setDamage(item.getDamage());
            PhysicsComponent pc = (PhysicsComponent)toBuy.getComponent("PhysicsComponent");
            pc.setHealth(item.getHealth());

            Application.get().getLogic().getGame().setCurrentMouseAction(new PlaceWeaponAction(toBuy, item.getValue()));
        }
    }
}
