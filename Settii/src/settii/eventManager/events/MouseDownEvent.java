/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class MouseDownEvent implements IGameEvent {
    public static long eventType = 8;
    
    private int mX, mY;
    private int button;
    
    public MouseDownEvent(int x, int y, int b) {
        mX = x;
        mY = y;
        button = b;
    }
    
    @Override
    public long getEventType() { 
        return eventType;
    }
    
    public int getX() {
        return mX;
    }
    public int getY() {
        return mY;
    }
    public int getButton() {
        return button;
    }
}
