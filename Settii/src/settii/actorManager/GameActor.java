package settii.actorManager;

import settii.actorManager.components.*;
import java.util.HashMap;
/**
 *
 * @author Merioksan Mikko
 */
public class GameActor {
    private long actorID;
    private HashMap<String, BaseComponent> components;
    
    public GameActor() {
        components = new HashMap<String, BaseComponent>();
    }
    
    public long getID() {
        return actorID;
    }
    
    public void addComponent(BaseComponent comp) {
        components.put(comp.getName(), comp);
    }
    public BaseComponent getComponent(String comp) {
        return components.get(comp);
    }
    
    public void move(double x, double y) {
        LocationComponent lc = (LocationComponent)getComponent("LocationComponent");
        
        if(lc != null) {
            lc.setLocation(x, y);
        }
    }
    
    public boolean locationWithinActor(double x, double y) {
        LocationComponent lc = (LocationComponent)getComponent("LocationComponent");
        HitboxComponent hc = (HitboxComponent)getComponent("HitboxComponent");
        
        if(lc != null && hc != null) {
            double relativeX = x - lc.getX();
            double relativeY = y - lc.getY();

            return hc.locationInsideHitbox(relativeX, relativeY);
        }
        
        return false;
    }
}
