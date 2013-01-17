package settii.eventManager.events;

import settii.actorManager.GameActor;
import settii.eventManager.IGameEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class FireWeaponEvent implements IGameEvent {
    public static long eventType = 1;
    
    private GameActor actor;
    
    public FireWeaponEvent(GameActor actor) {
        this.actor = actor;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public GameActor getActor() {
        return actor;
    }
}
