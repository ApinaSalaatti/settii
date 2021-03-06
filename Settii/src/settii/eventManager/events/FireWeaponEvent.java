package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class FireWeaponEvent implements IGameEvent {
    public static long eventType = 1;
    
    private long actor;
    
    public FireWeaponEvent(long actor) {
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
