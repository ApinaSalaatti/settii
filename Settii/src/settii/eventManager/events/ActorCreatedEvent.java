package settii.eventManager.events;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorCreatedEvent implements IGameEvent {
    public static long eventType = 3;
    
    private long actor;
    
    public ActorCreatedEvent(long id) {
        this.actor = id;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public long getActorID() {
        return actor;
    }
}
