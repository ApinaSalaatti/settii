package settii.views.humanView.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.ActorSelectedEvent;
import settii.views.humanView.HumanView;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorSelectedListener implements IGameEventListener {
    private HumanView hv;
    
    public ActorSelectedListener(HumanView hv) {
        this.hv = hv;
    }

    @Override
    public void execute(IGameEvent event) {
        ActorSelectedEvent ase = (ActorSelectedEvent)event;
        
        hv.actorSelectedListener(ase);
    }
}
