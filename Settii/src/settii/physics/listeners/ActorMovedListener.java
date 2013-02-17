package settii.physics.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.ActorMovedEvent;
import settii.physics.Physics;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorMovedListener implements IGameEventListener {
    private Physics physics;
    
    public ActorMovedListener(Physics p) {
        physics = p;
    }
    
    @Override
    public void execute(IGameEvent event) {
        ActorMovedEvent ame = (ActorMovedEvent)event;
        
        physics.actorMovedListener(ame.getActor());
    }
}
