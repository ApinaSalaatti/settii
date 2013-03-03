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
        
        screen.addScreenItem(new ShopButton(10, Display.getHeight() - 100, 104, 88));
        screen.addScreenItem(new StatusDisplay(0, 0, 40, Display.getWidth()));
        screen.addScreenItem(new SelectionDisplay(Display.getWidth() - 250, Display.getHeight() - 250));
        screen.addScreenItem(new CommandCenterButton(120, Display.getHeight() - 120));
        
        return screen;
    }
}
