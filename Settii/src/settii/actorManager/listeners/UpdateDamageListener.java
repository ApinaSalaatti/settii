package settii.actorManager.listeners;

import settii.eventManager.events.researchEvents.UpdateDamageEvent;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.shopEvents.*;
import settii.actorManager.ActorManager;
/**
 *
 * @author Merioksan Mikko
 */
public class UpdateDamageListener implements IGameEventListener {
    private ActorManager manager;
    
    public UpdateDamageListener(ActorManager am) {
        manager = am;
    }
    
    @Override
    public void execute(IGameEvent event){
        UpdateDamageEvent ude = (UpdateDamageEvent)event;
        
        manager.updateDamageListener(ude.getResource(), ude.getDamageToAdd());
    }
    
}
