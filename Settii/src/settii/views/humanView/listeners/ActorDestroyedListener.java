package settii.views.humanView.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.ActorDestroyedEvent;
import settii.views.humanView.GameScene;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorDestroyedListener implements IGameEventListener {
    private GameScene gs;
    
    public ActorDestroyedListener(GameScene scene) {
        gs = scene;
    }
    
    @Override
    public void execute(IGameEvent event) {
        ActorDestroyedEvent ade = (ActorDestroyedEvent)event;
        
        gs.actorDestroyedListener(ade.getActor());
    }
}
