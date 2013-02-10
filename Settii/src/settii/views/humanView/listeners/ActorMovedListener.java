package settii.views.humanView.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.ActorMovedEvent;
import settii.views.humanView.GameScene;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorMovedListener implements IGameEventListener {
    private GameScene gs;
    
    public ActorMovedListener(GameScene scene) {
        gs = scene;
    }
    
    @Override
    public void execute(IGameEvent event) {
        ActorMovedEvent ame = (ActorMovedEvent)event;
        
        gs.ActorMovedListener(ame);
    }
}
