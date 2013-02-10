package settii.eventManager.events;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorMovedEvent implements IGameEvent {
    public static long eventType = 4;
    
    private long actor;
    
    public ActorMovedEvent(long id) {
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
