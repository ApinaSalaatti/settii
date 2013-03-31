package settii.views.ui.shopScreen;

import java.util.Collection;
import settii.Application;
import settii.logic.shop.ShopItem;

/**
 *
 * @author Merioksan Mikko
 */
public class ShopScreenFactory {
    
    public static ShopScreen create() {
        ShopScreen ss = new ShopScreen();
        
        /*
        ss.addItemSlot(30, 400);
        ss.addItemSlot(120, 400);
        ss.addItemSlot(210, 400);
        ss.addItemSlot(300, 400);
        */
        
        Collection<ShopItem> items = Application.get().getLogic().getGame().getShop().getAvailableItems();
        int indx = 0;
        for(ShopItem i : items) {
            ss.addItemSlot(30 + indx * 90, 400, i);
            indx++;
        }
        
        ss.addScreenItem(new CancelButton(30, 490));
        
        return ss;
    }
    
    public static ShopScreen create(float x, float y) {
        ShopScreen ss = new ShopScreen();
        
        Collection<ShopItem> items = Application.get().getLogic().getGame().getShop().getAvailableItems();
        int indx = 0;
        for(ShopItem i : items) {
            ss.addItemSlot(x + 30 + indx * 90, y, i);
            indx++;
        }
        
        ss.addScreenItem(new CancelButton(x+30, y+90));
        
        return ss;
    }
}
