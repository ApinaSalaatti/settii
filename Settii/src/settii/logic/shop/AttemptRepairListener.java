package settii.logic.shop;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.AttemptToBuyEvent;
import settii.Application;

/**
 *
 * @author Merioksan Mikko
 */
public class AttemptRepairListener implements IGameEventListener {
    private Shop shop;
    
    public AttemptRepairListener(Shop s) {
        shop = s;
    }
    
    @Override
    public void execute(IGameEvent event) {
        shop.attemptRepairListener();
    }
}
