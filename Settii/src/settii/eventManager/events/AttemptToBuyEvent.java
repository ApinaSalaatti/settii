package settii.eventManager.events;

import settii.logic.shop.ShopItem;
/**
 *
 * @author Merioksan Mikko
 */
public class AttemptToBuyEvent implements IGameEvent {
    public static long eventType = 18;
    
    private ShopItem item;
    
    public AttemptToBuyEvent(ShopItem item) {
        this.item = item;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public int getCost() {
        return item.getValue();
    }
    public ShopItem getItem() {
        return item;
    }
}
