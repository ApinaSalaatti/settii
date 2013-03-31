package settii.eventManager.events.shopEvents;

/**
 *
 * @author Merioksan Mikko
 */
public class BuyActorEvent extends ShopEvent {
    public static long eventType = 20;
    
    public BuyActorEvent(int cost, String resource) {
        super(resource, cost);
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
}
