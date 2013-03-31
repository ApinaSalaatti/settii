package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class OptionsChangedEvent implements IGameEvent {
    public static long eventType = 29;
    
    public OptionsChangedEvent() {
        
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
}
