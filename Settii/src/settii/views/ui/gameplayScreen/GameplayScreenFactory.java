package settii.views.ui.gameplayScreen;

import settii.logic.GameLogic;
import settii.views.ui.*;
import settii.views.ui.screenItems.GameStateChangeButton;

import org.lwjgl.opengl.Display;
/**
 *
 * @author Merioksan Mikko
 */
public class GameplayScreenFactory {
    public GameplayScreenFactory() {
        
    }
    
    public static GameplayScreen create() {
        GameplayScreen screen = new GameplayScreen();
        
        screen.addScreenItem(new ShopButton(10, 200, 104, 88));
        screen.addScreenItem(new StatusDisplay(0, 0, 40, Display.getWidth()));
        screen.addScreenItem(new GameStateChangeButton(10, 310, 60, 60, GameLogic.GameState.QUITTING, "assets/graphics/ui/quit.png"));
        
        return screen;
    }
}
