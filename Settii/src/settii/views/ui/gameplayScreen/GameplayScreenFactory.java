package settii.views.ui.gameplayScreen;

import settii.views.ui.*;
/**
 *
 * @author Merioksan Mikko
 */
public class GameplayScreenFactory {
    public GameplayScreenFactory() {
        
    }
    
    public GameplayScreen create() {
        GameplayScreen screen = new GameplayScreen();
        
        screen.addScreenItem(new BaseScreenItem());
        
        return screen;
    }
}
