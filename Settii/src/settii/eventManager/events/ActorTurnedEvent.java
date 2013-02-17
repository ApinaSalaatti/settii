package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class ActorTurnedEvent implements IGameEvent {
    public static long eventType = 14;
    
    private long actor;
    
    public ActorTurnedEvent(long id) {
        this.actor = id;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public long getActor() {
        return actor;
    }
}
