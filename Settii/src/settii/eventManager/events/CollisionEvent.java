package settii.eventManager.events;

import settii.actorManager.GameActor;
/**
 *
 * @author Merioksan Mikko
 */
public class CollisionEvent implements IGameEvent {
    public static long eventType = 16;
    
    private GameActor a1;
    private GameActor a2;
    
    public CollisionEvent(GameActor a1, GameActor a2) {
        this.a1 = a1;
        this.a2 = a2;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public GameActor getActor1() {
        return a1;
    }
    public GameActor getActor2() {
        return a2;
    }
}
