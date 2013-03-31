package settii.views.ui.mainMenuScreen;

import settii.logic.GameLogic;
import settii.views.ui.screenItems.GameStateChangeButton;

/**
 *
 * @author Merioksan Mikko
 */
public class MainMenuScreenFactory {
    public static MainMenuScreen create() {
        MainMenuScreen mms = new MainMenuScreen();
        
        mms.addScreenItem(new Background());
        StartGameButton sgb = new StartGameButton(50, 50);
        sgb.setVisible(false);
        mms.addScreenItem(sgb);
        GameStateChangeButton gscb = new GameStateChangeButton(50, 350, 500, 250, GameLogic.GameState.QUITTING, "assets/graphics/ui/mainmenuscreen/quitButton.png");
        gscb.setVisible(false);
        mms.addScreenItem(gscb);
        
        return mms;
    }
}
