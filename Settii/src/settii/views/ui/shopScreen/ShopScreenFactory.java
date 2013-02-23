package settii.views.ui.shopScreen;

/**
 *
 * @author Merioksan Mikko
 */
public class ShopScreenFactory {
    
    public static ShopScreen create() {
        ShopScreen ss = new ShopScreen();
        
        ss.addItemSlot(30, 400);
        ss.addItemSlot(120, 400);
        // ss.addScreenItem(new BuyButton(30, 500));
        ss.addScreenItem(new CancelButton(30, 490));
        
        return ss;
    }
}
