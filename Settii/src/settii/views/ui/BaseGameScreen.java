package settii.views.ui;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class BaseGameScreen implements IGameScreen {
    protected ArrayDeque<IScreenItem> screenItems;
    
    public BaseGameScreen() {
        screenItems = new ArrayDeque<IScreenItem>();
    }
    
    @Override
    public void addScreenItem(IScreenItem item) {
        screenItems.add(item);
    }
    
    @Override
    public void render(Graphics2D g) {
        Iterator<IScreenItem> it = screenItems.descendingIterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.isVisible()) {
                item.render(g);
            }
        }
    }
    
    @Override
    public boolean onButtonDown(InputEvent e) {
        Iterator<IScreenItem> it = screenItems.iterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.onButtonDown(e)) {
                return true;
            }
        }
        
        return false;
    }
    @Override
    public boolean onButtonUp(InputEvent e) {
        Iterator<IScreenItem> it = screenItems.iterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.onButtonUp(e)) {
                return true;
            }
        }
        
        return false;
    }
    @Override
    public boolean onPointerMove(MouseEvent e) {
        Iterator<IScreenItem> it = screenItems.iterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.onPointerMove(e)) {
                return true;
            }
        }
        
        return false;
    }
}
