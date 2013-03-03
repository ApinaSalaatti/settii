package settii.views.ui.shopScreen;

import java.util.Iterator;
import settii.views.ui.*;
import java.util.ArrayList;
import java.util.Collection;
import settii.logic.shop.ShopItem;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class ShopScreen extends BaseGameScreen {
    private ArrayList<ShopItemButton> itemSlots;
    
    public ShopScreen() {
        super();
        itemSlots = new ArrayList<ShopItemButton>();
    }
    
    public void addItemSlot(float x, float y, ShopItem i) {
        itemSlots.add(new ShopItemButton(x, y, i));
    }
    
    @Override
    public void render() {
        super.render();
        /*
        Collection<ShopItem> items = Application.get().getLogic().getGame().getShop().getAvailableItems();
        int indx = 0;
        for(ShopItem i : items) {
            itemSlots.get(indx).setItem(i);
            indx++;
        }
        */
        
        for(ShopItemButton sib : itemSlots) {
            sib.render();
        }
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(super.onMouseDown(mX, mY, button)) {
            return true;
        }
        for(ShopItemButton sib : itemSlots) {
            if(sib.onMouseDown(mX, mY, button)) {
                return true;
            }
        }
        
        return true;
    }
}
