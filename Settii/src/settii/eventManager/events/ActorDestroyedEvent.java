package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class ActorDestroyedEvent implements IGameEvent {
    public static long eventType = 15;
    
    private long actor;
    
    public ActorDestroyedEvent(long id) {
        actor = id;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public long getActor() {
        return actor;
    }
}
