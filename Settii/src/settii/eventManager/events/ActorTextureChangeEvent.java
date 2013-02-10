/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.eventManager.events;

/**
 *
 * @author Merioksan Mikko
 */
public class ActorTextureChangeEvent implements IGameEvent {
    public static long eventType = 13;
    
    private long actor;
    private String texture;
    
    public ActorTextureChangeEvent(long a, String tex) {
        actor = a;
        texture = tex;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public long getActor() {
        return actor;
    }
    public String getTexture() {
        return texture;
    }
}
