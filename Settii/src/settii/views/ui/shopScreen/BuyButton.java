package settii.views.ui.shopScreen;

import settii.Application;
import settii.logic.mouse.*;
import settii.views.ui.BaseScreenItem;
import settii.actorManager.GameActor;
/**
 *
 * @author Merioksan Mikko
 */
public class BuyButton extends BaseScreenItem {

    public BuyButton(float x, float y) {
        super(x, y, 128, 128, "assets/graphics/ui/shopscreen/buyButton.png");
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(mX > x && mX < x + width && mY > y && mY < y + height) {
            GameActor toBuy = Application.get().getLogic().getActor(Application.get().getLogic().createActor("assets/data/actors/cannon.xml"));
            //Application.get().getLogic().getGame().setCurrentMouseAction(new PlaceWeaponAction(toBuy));
            Application.get().getHumanView().popScreen();
            return true;
        }
        
        return false;
    }
}
