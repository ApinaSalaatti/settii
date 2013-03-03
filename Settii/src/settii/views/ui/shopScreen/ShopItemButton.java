package settii.views.ui.shopScreen;

import settii.Application;
import settii.logic.shop.ShopItem;
import settii.actorManager.GameActor;
import settii.actorManager.components.*;
import settii.logic.mouse.PlaceWeaponAction;
import settii.views.ui.BaseScreenItem;
import settii.views.humanView.renderer.Texture;
import settii.views.humanView.renderer.Renderer;
import settii.eventManager.events.*;
/**
 *
 * @author Merioksan Mikko
 */
public class ShopItemButton extends BaseScreenItem {
    ShopItem item;
    
    public ShopItemButton(float x, float y, ShopItem i) {
        super(x, y, 60, 60, "assets/graphics/ui/shopscreen/shopItem.png");
        item = i;
    }
    
    public void setItem(ShopItem i) {
        item = i;
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(button == 0 && mX > x && mX < x + width && mY > y && mY < y + height) {
            if(item != null) {
                Application.get().getEventManager().queueEvent(new AttemptToBuyEvent(item));
                
                Application.get().getHumanView().popScreen();
            }
            return true;
        }
        
        return false;
    }
    
    @Override
    public void render() {
        super.render();
        if(item != null) {
            Texture tex = Application.get().getResourceManager().getTextureManager().getTexture(item.getSprite());
            Renderer.get().draw(tex, x, y);
            
            Renderer.get().drawText("Cost:" + item.getValue(), x, y+70, 0.5f);
        }
    }
}
