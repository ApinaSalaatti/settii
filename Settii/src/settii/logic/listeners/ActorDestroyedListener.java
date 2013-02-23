package settii.logic.listeners;

import settii.logic.IGameLogic;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.ActorDestroyedEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorDestroyedListener implements IGameEventListener {
    private IGameLogic gl;
    
    public ActorDestroyedListener(IGameLogic gl) {
        this.gl = gl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        ActorDestroyedEvent ade = (ActorDestroyedEvent)event;
        
        gl.actorDestroyedListener(ade.getActor());
    }
}
