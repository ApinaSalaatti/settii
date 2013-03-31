package settii.views.ui.screenItems;

import settii.Application;
import settii.eventManager.events.GameStateChangeEvent;
import settii.logic.GameLogic;
import settii.views.ui.BaseScreenItem;
/**
 *
 * @author Merioksan Mikko
 */
public class GameStateChangeButton extends BaseScreenItem {
    private GameLogic.GameState state;
    
    public GameStateChangeButton(float x, float y, float w, float h, GameLogic.GameState s, String res) {
        super(x, y, w, h, res);
        state = s;
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(button == 0 && mX > x && mX < x + width && mY > y && mY < y + height) {
            Application.get().getEventManager().queueEvent(new GameStateChangeEvent(state));
            return true;
        }
        
        return false;
    }
}
