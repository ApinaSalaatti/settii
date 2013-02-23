package settii.eventManager.events;

import settii.actorManager.GameActor;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorDestroyedEvent implements IGameEvent {
    public static long eventType = 17;
    
    private GameActor actor;
    
    public ActorDestroyedEvent(GameActor a) {
        actor = a;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public GameActor getActor() {
        return actor;
    }
}
