package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class SelectedActorsClearedEvent implements IGameEvent {
    public static long eventType = 7;
    
    @Override
    public long getEventType() {
        return eventType;
    }
}
