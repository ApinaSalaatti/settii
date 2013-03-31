package settii.views.ui.highScoreScreen;

import settii.logic.GameLogic;
import settii.views.ui.screenItems.GameStateChangeButton;

/**
 *
 * @author Merioksan Mikko
 */
public class HighScoreScreenFactory {
    public static HighScoreScreen create() {
        HighScoreScreen hss = new HighScoreScreen();
        
        //hss.addScreenItem(new ScoreDisplay());
        //hss.addScreenItem(new ScoreInput());
        hss.addScreenItem(new GameStateChangeButton(400, 500, 120, 75, GameLogic.GameState.MAIN_MENU, "assets/graphics/ui/highscorescreen/continueButton.png"));
        
        return hss;
    }
}
