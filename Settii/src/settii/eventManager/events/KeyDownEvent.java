/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class KeyDownEvent implements IGameEvent {
    public static long eventType = 10;
    
    private int key;
    
    public KeyDownEvent(int k) {
        key = k;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public int getKey() {
        return key;
    }
}
