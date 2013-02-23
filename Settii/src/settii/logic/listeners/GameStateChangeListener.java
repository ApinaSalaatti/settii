package settii.logic.listeners;


import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.GameStateChangeEvent;
import settii.logic.GameLogic;
/**
 *
 * @author Merioksan Mikko
 */
public class GameStateChangeListener implements IGameEventListener {
    private GameLogic logic;
    
    public GameStateChangeListener(GameLogic gl) {
        logic = gl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        GameStateChangeEvent gsce = (GameStateChangeEvent)event;
        
        logic.gameStateChangeListener(gsce.getState());
    }
}
