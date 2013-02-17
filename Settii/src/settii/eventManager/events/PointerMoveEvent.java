/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class PointerMoveEvent implements IGameEvent {
    public static long eventType = 12;
    
    private int mDX, mDY;
    private int mX, mY;
    
    public PointerMoveEvent(int x, int y, int dx, int dy) {
        mX = x;
        mY = y;
        mDX = dx;
        mDY = dy;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public int getDX() {
        return mDX;
    }
    public int getDY() {
        return mDY;
    }
    public int getX() {
        return mX;
    }
    public int getY() {
        return mY;
    }
}
