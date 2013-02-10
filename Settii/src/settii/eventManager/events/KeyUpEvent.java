/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class KeyUpEvent implements IGameEvent {
    public static long eventType = 11;
    
    private int key;
    
    public KeyUpEvent(int k) {
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
