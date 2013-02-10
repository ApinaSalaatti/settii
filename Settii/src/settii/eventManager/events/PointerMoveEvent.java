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
    
    public PointerMoveEvent(int dx, int dy) {
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
}
