package settii.logic.shop;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.AttemptToBuyEvent;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class AttemptToBuyListener implements IGameEventListener {
    private Shop shop;
    
    public AttemptToBuyListener(Shop s) {
        shop = s;
    }
    
    @Override
    public void execute(IGameEvent event) {
        AttemptToBuyEvent atbe = (AttemptToBuyEvent)event;
        shop.attemptToBuyListener(atbe.getItem());
    }
}
