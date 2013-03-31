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
        screenItems.addFirst(item);
    }
    
    @Override
    public void render() {
        Iterator<IScreenItem> it = screenItems.descendingIterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.isVisible()) {
                item.render();
            }
        }
    }
    
    @Override
    public void update(long deltaMs) {
        Iterator<IScreenItem> it = screenItems.descendingIterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            item.update(deltaMs);
        }
    }
    
    @Override
    public void makeVisible(boolean v) {
        for(IScreenItem item : screenItems) {
            item.setVisible(v);
        }
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        Iterator<IScreenItem> it = screenItems.iterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.onMouseDown(mX, mY, button)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean onMouseUp(int mX, int mY, int button) {
        Iterator<IScreenItem> it = screenItems.iterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.onMouseUp(mX, mY, button)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean onKeyDown(int key) {
        Iterator<IScreenItem> it = screenItems.iterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.onKeyDown(key)) {
                return true;
            }
        }
        
        return false;
    }
    @Override
    public boolean onKeyUp(int key) {
        Iterator<IScreenItem> it = screenItems.iterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.onKeyUp(key)) {
                return true;
            }
        }
        
        return false;
    }
    @Override
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY) {
        Iterator<IScreenItem> it = screenItems.iterator();
        
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(item.onPointerMove(mX, mY, mDX, mDY)) {
                return true;
            }
        }
        
        return false;
    }
}
