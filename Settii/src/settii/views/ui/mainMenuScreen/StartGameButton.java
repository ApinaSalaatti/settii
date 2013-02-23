package settii.views.ui.mainMenuScreen;

import settii.views.ui.BaseScreenItem;
/**
 *
 * @author Merioksan Mikko
 */
public class StartGameButton extends BaseScreenItem {
    
    public StartGameButton(float x, float y) {
        super(x, y, 500, 250, "assets/graphics/ui/mainmenuscreen/startGameButton.png");
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        return false;
    }
}
