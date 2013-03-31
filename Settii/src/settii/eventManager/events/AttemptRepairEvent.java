package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class AttemptRepairEvent implements IGameEvent {
    public static long eventType = 26;
    
    public AttemptRepairEvent() {
        
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
}
