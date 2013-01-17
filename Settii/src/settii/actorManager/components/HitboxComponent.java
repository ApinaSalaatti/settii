package settii.actorManager.components;

import settii.actorManager.BaseComponent;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author Merioksan Mikko
 */
public class HitboxComponent extends BaseComponent {
    private ArrayList<Rectangle> hitboxes;
    
    public HitboxComponent() {
        hitboxes = new ArrayList<Rectangle>();
    }
    public HitboxComponent(ArrayList<Rectangle> boxes) {
        hitboxes = boxes;
    }
    
    @Override
    public String getName() {
        return "HitboxComponent";
    }
    
    /**
     * Checks if given location is within any of the hitboxes. The locations of hitboxes are relative to the location of the actor.
     * @param x x coord relative to actor
     * @param y y coord relative to actor
     * @return true if given location is within our hitbox
     */
    public boolean locationInsideHitbox(double x, double y) {
        Iterator<Rectangle> it = hitboxes.iterator();
        while(it.hasNext()) {
            Rectangle rec = it.next();
            if(x > rec.x && x < rec.x + rec.width && y > rec.y && y < rec.y + rec.height) {
                return true;
            }
        }
        
        return false;
    }
}
