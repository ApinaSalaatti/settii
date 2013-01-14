package settii.views.ui.gameplayScreen;

import java.util.Iterator;
import settii.views.ui.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class GameplayScreen extends BaseGameScreen {
    
    @Override
    public boolean onPointerMove(MouseEvent e) {
        Iterator<IScreenItem> it = screenItems.iterator();
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.isSelected()) {
                item.onPointerMove(e);
            }
        }
        
        return true;
    }
}
