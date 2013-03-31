package settii.views.ui.pauseScreen;

import org.lwjgl.input.Keyboard;
import settii.Application;
import settii.eventManager.events.GameStateChangeEvent;
import settii.logic.GameLogic;
import settii.views.ui.BaseGameScreen;

/**
 *
 * @author Merioksan Mikko
 */
public class PauseScreen extends BaseGameScreen {
    
    @Override
    public boolean onKeyDown(int key) {
        if(super.onKeyDown(key)) {
            return true;
        }
        
        if(key == Keyboard.KEY_ESCAPE) {
            Application.get().getEventManager().queueEvent(new GameStateChangeEvent(GameLogic.GameState.PLAYING));
            return true;
        }
        
        return false;
    }
}
