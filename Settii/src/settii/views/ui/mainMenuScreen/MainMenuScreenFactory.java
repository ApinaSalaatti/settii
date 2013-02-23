package settii.views.ui.mainMenuScreen;

/**
 *
 * @author Merioksan Mikko
 */
public class MainMenuScreenFactory {
    public static MainMenuScreen create() {
        MainMenuScreen mms = new MainMenuScreen();
        
        mms.addScreenItem(new StartGameButton(50, 50));
        
        return mms;
    }
}
