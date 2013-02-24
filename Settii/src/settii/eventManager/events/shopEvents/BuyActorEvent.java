package settii.eventManager.events.shopEvents;

/**
 *
 * @author Merioksan Mikko
 */
public class BuyActorEvent extends ShopEvent {
    public static long eventType = 20;
    
    private String actorRes;
    private int cost;
    
    public BuyActorEvent(int cost, String resource) {
        actorRes = resource;
        this.cost = cost;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public String getResource() {
        return actorRes;
    }
    
    public int getCost() {
        return cost;
    }
}
