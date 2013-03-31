package settii.actorManager.ai;

import org.w3c.dom.NodeList;
import settii.Application;
import settii.actorManager.GameActor;

/**
 *
 * @author Merioksan Mikko
 */
public abstract class BaseAI {
    protected long owner;
    
    public BaseAI(long owner) {
        this.owner = owner;
    }
    
    public GameActor getOwningActor() {
        return Application.get().getLogic().getActor(owner);
    }
    
    public abstract void update(long deltaMs);
    public abstract void createFromXML(NodeList attributes);
    
    /*
     * This can be overriden as needed. Called when the actor is destroyed.
     */
    public void destroy() {
        
    }
}
