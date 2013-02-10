package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class ActorSelectedEvent implements IGameEvent {
    public static long eventType = 6;
    
    private long actor;
    
    public ActorSelectedEvent(long id) {
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
