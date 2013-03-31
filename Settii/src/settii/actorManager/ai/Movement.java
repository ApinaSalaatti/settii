package settii.actorManager.ai;

import org.w3c.dom.NodeList;

/**
 *
 * @author Merioksan Mikko
 */
public abstract class Movement extends BaseAI {
    protected float targetX, targetY;
    
    public Movement(long owner) {
        super(owner);
        // first se invalid values
        targetX = -1;
        targetY = -1;
    }
    
    public void setTarget(float x, float y) {
        targetX = x;
        targetY = y;
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        
    }
}
