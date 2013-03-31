package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class WeaponFiredEvent implements IGameEvent {
    public static long eventType = 28;
    
    private long actor;
    
    public WeaponFiredEvent(long actor) {
        this.actor = actor;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public long getActor() {
        return actor;
    }
}
