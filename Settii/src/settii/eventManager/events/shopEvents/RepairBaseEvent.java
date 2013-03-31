package settii.eventManager.events.shopEvents;

/**
 *
 * @author Merioksan Mikko
 */
public class RepairBaseEvent extends ShopEvent {
    public static long eventType = 25;
    
    public RepairBaseEvent(int cost) {
        super(null, cost);
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
}
