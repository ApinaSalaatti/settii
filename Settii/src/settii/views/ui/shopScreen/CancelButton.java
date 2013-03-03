package settii.views.ui.shopScreen;

import settii.Application;
import settii.views.ui.BaseScreenItem;
/**
 *
 * @author Merioksan Mikko
 */
public class CancelButton extends BaseScreenItem {

    public CancelButton(float x, float y) {
        super(x, y, 128, 128, "assets/graphics/ui/shopscreen/cancelButton.png");
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
