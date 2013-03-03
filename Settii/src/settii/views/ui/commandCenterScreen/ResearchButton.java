package settii.views.ui.commandCenterScreen;

import settii.Application;
import settii.views.ui.BaseScreenItem;
import settii.views.ui.researchScreen.ResearchScreenFactory;
/**
 *
 * @author Merioksan Mikko
 */
public class ResearchButton extends BaseScreenItem {
    public ResearchButton(int x, int y) {
        super(x, y, 107, 100, "assets/graphics/ui/commandcenterscreen/researchButton.png");
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(button == 0 && mX > x && mX < x + width && mY > y && mY < y + height) {
            Application.get().getHumanView().addScreen(ResearchScreenFactory.create());
            return true;
        }
        return false;
    }
}
