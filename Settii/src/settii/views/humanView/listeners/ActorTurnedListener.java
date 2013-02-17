package settii.views.humanView.listeners;

import settii.views.humanView.GameScene;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.ActorTurnedEvent;
import settii.eventManager.events.IGameEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorTurnedListener implements IGameEventListener {
    private GameScene gs;
    
    public ActorTurnedListener(GameScene gs) {
        this.gs = gs;
    }
    
    @Override
    public void execute(IGameEvent event) {
        ActorTurnedEvent ate = (ActorTurnedEvent)event;
        
        gs.ActorTurnedListener(ate.getActor());
    }
}
