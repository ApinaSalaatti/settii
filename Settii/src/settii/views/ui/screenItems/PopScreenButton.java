package settii.views.ui.screenItems;

import settii.Application;
import settii.views.ui.BaseScreenItem;
/**
 *
 * @author Merioksan Mikko
 */
public class PopScreenButton extends BaseScreenItem {
    public PopScreenButton(float x, float y, float w, float h, String res) {
        super(x, y, w, h, res);
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(button == 0 && mX > x && mX < x + width && mY > y && mY < y + height) {
            Application.get().getHumanView().popScreen();
            return true;
        }
        return false;
    }
}
