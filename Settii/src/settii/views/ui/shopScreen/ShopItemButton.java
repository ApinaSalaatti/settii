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
        if(mX > x && mX < x + width && mY > y && mY < y + height) {
            if(item != null) {
                GameActor toBuy = Application.get().getLogic().getActor(Application.get().getLogic().createActor(item.getResource()));

                WeaponsComponent wc = (WeaponsComponent)toBuy.getComponent("WeaponsComponent");
                wc.setDamage(item.getDamage());
                PhysicsComponent pc = (PhysicsComponent)toBuy.getComponent("PhysicsComponent");
                pc.setHealth(item.getHealth());
                
                Application.get().getEventManager().queueEvent(new AttemptToBuyEvent(item));
                
                //Application.get().getLogic().getGame().setCurrentMouseAction(new PlaceWeaponAction(toBuy));
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
            Renderer.get().drawText("D:" + item.getDamage(), x, y+90, 0.5f);
        }
    }
}
