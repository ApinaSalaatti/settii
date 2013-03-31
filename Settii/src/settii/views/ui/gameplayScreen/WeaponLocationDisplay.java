package settii.views.ui.gameplayScreen;

import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import settii.Application;
import settii.logic.WeaponLocation;
import settii.views.humanView.renderer.Renderer;
import settii.views.humanView.renderer.Texture;
import settii.views.ui.BaseScreenItem;

/**
 *
 * @author Merioksan Mikko
 */
public class WeaponLocationDisplay extends BaseScreenItem {
    private WeaponLocation location;
    private WeaponLocationData locData;
    private boolean renderData;
    
    public WeaponLocationDisplay(WeaponLocation loc) {
        super(loc.getX(), loc.getY(), 50, 50, "assets/graphics/ui/gameplayscreen/weaponLocation.png");
        //System.out.println(loc.getX() + " " + loc.getY());
        location = loc;
        locData = new WeaponLocationData(loc);
        renderData = false;
    }
    
    @Override
    public void render() {
        if(location.getWeaponID() == WeaponLocation.NO_WEAPON) {
            super.render();
        }
        
        if(renderData) {
            locData.render();
        }
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(renderData) {
            if(locData.onMouseDown(mX, mY, button)) {
                return true;
            }
        }
        
        if(button == 0 && mX > x && mX < x + width && mY > y && mY < y + height) {
            renderData = true;
            locData.updateShop();
            return true;
        }
        
        renderData = false;
        
        return false;
    }
}
