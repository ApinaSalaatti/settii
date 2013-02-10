package settii.logic.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.ActorSelectedEvent;
import settii.logic.SettiLogic;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorSelectedListener implements IGameEventListener {
    private SettiLogic sl;
    
    public ActorSelectedListener(SettiLogic sl) {
        this.sl = sl;
    }

    @Override
    public void execute(IGameEvent event) {
        ActorSelectedEvent ase = (ActorSelectedEvent)event;
        
        sl.actorSelectedListener(ase);
    }
}
