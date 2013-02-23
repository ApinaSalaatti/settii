package settii.views.ui.gameplayScreen;

import settii.views.ui.BaseScreenItem;
import settii.Application;
import settii.views.ui.shopScreen.ShopScreenFactory;
/**
 *
 * @author Merioksan Mikko
 */
public class ShopButton extends BaseScreenItem {
 
    public ShopButton(float x, float y, float w, float h) {
        super(x, y, w, h, "assets/graphics/ui/gameplayscreen/shopButton.png");
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(mX > x && mX < x + width && mY > y && mY < y + height) {
            Application.get().getHumanView().addScreen(ShopScreenFactory.create());
            return true;
        }
        
        return false;
    }
}
