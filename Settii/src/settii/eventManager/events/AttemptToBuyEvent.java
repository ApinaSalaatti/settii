package settii.eventManager.events;

import settii.logic.shop.Sellable;
/**
 *
 * @author Merioksan Mikko
 */
public class AttemptToBuyEvent implements IGameEvent {
    public static long eventType = 18;
    
    private Sellable item;
    
    public AttemptToBuyEvent(Sellable item) {
        this.item = item;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public int getCost() {
        return item.getValue();
    }
    public Sellable getItem() {
        return item;
    }
}
