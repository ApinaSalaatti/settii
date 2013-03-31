package settii.views.ui.mainMenuScreen;

import settii.logic.GameLogic;
import settii.views.ui.screenItems.GameStateChangeButton;
/**
 *
 * @author Merioksan Mikko
 */
public class StartGameButton extends GameStateChangeButton {
    
    public StartGameButton(float x, float y) {
        super(x, y, 500, 250, GameLogic.GameState.PLAYING, "assets/graphics/ui/mainmenuscreen/startGameButton.png");
    }
}
