package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class ActorSelectedEvent implements IGameEvent {
    public static long eventType = 6;
    
    private long actor;
    private boolean selected;
    
    public ActorSelectedEvent(long id, boolean s) {
        actor = id;
        selected = s;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public long getActor() {
        return actor;
    }
    public boolean selected() {
        return selected;
    }
}
