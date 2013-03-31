package settii.views.ui.mainMenuScreen;

import java.util.Iterator;
import settii.views.ui.BaseGameScreen;
import settii.views.ui.IScreenItem;
/**
 *
 * @author Merioksan Mikko
 */
public class MainMenuScreen extends BaseGameScreen {
    private long logoDelay;
    private long elapsed;
    private boolean hideMenu;
    
    public MainMenuScreen() {
        super();
        logoDelay = 2000;
        elapsed = 0;
        hideMenu = true;
    }
    
    @Override
    public void update(long deltaMs) {
        super.update(deltaMs);
        
        if(hideMenu) {
            elapsed += deltaMs; 
            if(elapsed >= logoDelay) {
                hideMenu = false;
                super.makeVisible(true);
            }
        }
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        Iterator<IScreenItem> it = screenItems.iterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.isVisible() && item.onMouseDown(mX, mY, button)) {
                return true;
            }
        }
        return false;
    }
}
