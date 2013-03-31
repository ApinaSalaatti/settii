package settii.eventManager.events;

import settii.actorManager.GameActor;

/**
 *
 * @author Merioksan Mikko
 */
public class EnemyDestroyedEvent implements IGameEvent {
    public static long eventType = 27;
    
    private GameActor actor;
    
    public EnemyDestroyedEvent(GameActor actor) {
        this.actor = actor;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public GameActor getActor() {
        return actor;
    }
}
