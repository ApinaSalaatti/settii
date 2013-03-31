package settii.actorManager.listeners;

import settii.eventManager.events.researchEvents.UpdateRangeEvent;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.shopEvents.*;
import settii.actorManager.ActorManager;
/**
 *
 * @author Merioksan Mikko
 */
public class UpdateRangeListener implements IGameEventListener {
    private ActorManager manager;
    
    public UpdateRangeListener(ActorManager am) {
        manager = am;
    }
    
    @Override
    public void execute(IGameEvent event) {
        UpdateRangeEvent ure = (UpdateRangeEvent)event;
        manager.updateRangeListener(ure.getResource(), ure.getRangeToAdd());
    }
}
