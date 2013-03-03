package settii.views.ui.gameplayScreen;

import settii.Application;
import settii.views.ui.BaseScreenItem;
import settii.views.ui.commandCenterScreen.CommandCenterScreenFactory;
/**
 *
 * @author Merioksan Mikko
 */
public class CommandCenterButton extends BaseScreenItem {
    public CommandCenterButton(int x, int y) {
        super(x, y, 256, 115, "assets/graphics/ui/gameplayscreen/commandCenterButton.png");
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(button == 0 && mX > x && mX < x + width && mY > y && mY < y + height) {
            Application.get().getHumanView().addScreen(CommandCenterScreenFactory.create());
            return true;
        }
        
        return false;
    }
}
