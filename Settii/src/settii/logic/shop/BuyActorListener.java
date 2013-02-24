package settii.logic.shop;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.shopEvents.BuyActorEvent;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class BuyActorListener implements IGameEventListener {
    private Shop shop;
    
    public BuyActorListener(Shop s) {
        shop = s;
    }
    
    @Override
    public void execute(IGameEvent event) {
        BuyActorEvent bae = (BuyActorEvent)event;
        
        shop.buyActorListener(bae.getResource(), bae.getCost());
    }
}
