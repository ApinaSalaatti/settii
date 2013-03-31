package settii.views.ui.gameplayScreen;

import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.StatusComponent;
import settii.logic.WeaponLocation;
import settii.views.humanView.renderer.Renderer;
import settii.views.ui.BaseScreenItem;
import settii.views.ui.shopScreen.ShopScreen;
import settii.views.ui.shopScreen.ShopScreenFactory;

/**
 *
 * @author Merioksan Mikko
 */
public class WeaponLocationData extends BaseScreenItem {
    private WeaponLocation loc;
    private ShopScreen shop;
    
    public WeaponLocationData(WeaponLocation wl) {
        super(wl.getX(), wl.getY(), 50, 50, null);
        loc = wl;
    }
    
    @Override
    public void render() {
        super.render();
        if(loc.getWeaponID() == WeaponLocation.NO_WEAPON) {
            Renderer.get().drawText("Add a weapon:", x, y);
            shop.render();
        }
        else {
            GameActor a = Application.get().getLogic().getActor(loc.getWeaponID());
            StatusComponent sc = (StatusComponent)a.getComponent("StatusComponent");
            Renderer.get().drawText("Remove weapon:" + sc.getActorName(), x, y);
        }
    }
    
    public void updateShop() {
        shop = ShopScreenFactory.create(x, y);
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(loc.getWeaponID() == WeaponLocation.NO_WEAPON && shop.onMouseDown(mX, mY, button)) {
            return true;
        }
        return false;
    }
}
