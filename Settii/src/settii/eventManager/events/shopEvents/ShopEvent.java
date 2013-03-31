package settii.eventManager.events.shopEvents;

import settii.eventManager.events.*;
/**
 *
 * @author Merioksan Mikko
 */
public abstract class ShopEvent implements IGameEvent {
    protected String actorRes;
    protected int cost;
    
    public ShopEvent(String res, int c) {
        actorRes = res;
        cost = c;
    }
    
    public String getResource() {
        return actorRes;
    }
    
    public int getCost() {
        return cost;
    }
}
