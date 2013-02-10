package settii.eventManager.events;

import settii.views.humanView.RenderObject;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class RenderableActorCreatedEvent implements IGameEvent {
    public static long eventType = 2;
    
    private RenderObject ro;
    
    public RenderableActorCreatedEvent(RenderObject ro) {
        this.ro = ro;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public RenderObject getRenderObject() {
        return ro;
    }
}
