package settii.actorManager.ai;

import org.w3c.dom.NodeList;
import settii.actorManager.GameActor;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public abstract class Targeting extends BaseAI {
    protected long targetID;
    protected String type;
    
    public Targeting(long owner, String type) {
        super(owner);
        this.type = type;
    }
    
    public void setTarget(long id) {
        targetID = id;
    }
    
    public String getType() {
        return type;
    }
    
    public GameActor getTargetedActor() {
        return Application.get().getLogic().getActor(targetID);
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        
    }
}
