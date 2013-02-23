package settii.eventManager.events;

import settii.logic.GameLogic;
/**
 *
 * @author Merioksan Mikko
 */
public class GameStateChangeEvent implements IGameEvent {
    public static long eventType = 19;
    
    private GameLogic.GameState state;
    
    public GameStateChangeEvent(GameLogic.GameState s) {
        state = s;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public GameLogic.GameState getState() {
        return state;
    }
}
