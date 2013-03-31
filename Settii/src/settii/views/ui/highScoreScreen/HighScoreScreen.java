package settii.views.ui.highScoreScreen;

import org.lwjgl.input.Keyboard;
import settii.Application;
import settii.eventManager.events.GameStateChangeEvent;
import settii.logic.GameLogic;
import settii.views.ui.BaseGameScreen;

/**
 *
 * @author Merioksan Mikko
 */
public class HighScoreScreen extends BaseGameScreen {
    private ScoreInput input;
    private ScoreDisplay display;
    
    public HighScoreScreen() {
        input = new ScoreInput();
        display = new ScoreDisplay();
    }

    @Override
    public boolean onKeyDown(int key) {
        if(super.onKeyDown(key)) {
            return true;
        }
        
        if(input.onKeyDown(key)) {
            display.updateScores();
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(super.onMouseDown(mX, mY, button)) {
            return true;
        }
        
        if(input.onMouseDown(mX, mY, button) || display.onMouseDown(mX, mY, button)) {
            return true;
        }
        
        return false;
    }
    
    @Override
    public void render() {
        display.render();
        input.render();
        super.render();
    }
}
