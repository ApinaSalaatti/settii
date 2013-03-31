package settii.views.ui.pauseScreen;

import org.lwjgl.opengl.Display;
import settii.logic.GameLogic;
import settii.views.ui.screenItems.GameStateChangeButton;

/**
 *
 * @author Merioksan Mikko
 */
public class PauseScreenFactory {
    public static PauseScreen create() {
        PauseScreen ps = new PauseScreen();
        
        ps.addScreenItem(new GameStateChangeButton(25, Display.getHeight() / 2 + 190, 60, 60, GameLogic.GameState.QUITTING, "assets/graphics/ui/quit.png"));
        
        return ps;
    }
}
