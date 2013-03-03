package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class ChangeSectorEvent implements IGameEvent {
    public static long eventType = 22;
    
    private long id;
    
    public ChangeSectorEvent(long id) {
        this.id = id;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public long getID() {
        return id;
    }
}
