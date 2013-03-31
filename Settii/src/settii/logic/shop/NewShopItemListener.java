package settii.logic.shop;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.researchEvents.NewShopItemEvent;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class NewShopItemListener implements IGameEventListener {
    private Shop shop;
    
    public NewShopItemListener(Shop s) {
        shop = s;
    }
    
    @Override
    public void execute(IGameEvent event) {
        NewShopItemEvent nsie = (NewShopItemEvent)event;
        
        shop.newShopItemListener(nsie.getResource());
    }
}
