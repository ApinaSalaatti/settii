package settii.actorManager.ai;

import org.w3c.dom.NodeList;

/**
 *
 * @author Merioksan Mikko
 */
public class EnemyMovement extends Movement {
    private boolean timeForNewTarget;
    
    private long currentSector;
    
    public EnemyMovement(long owner) {
        super(owner);
        timeForNewTarget = true;
        currentSector = -1;
    }
    
    @Override
    public void update(long deltaMs) {
        if(targetX < 0 || targetY < 0 || timeForNewTarget) {
            getTarget();
        }
        
        if(targetX >= 0 && targetY >= 0) {
            move();
        }
    }
    
    public void setSector(long id) {
        currentSector = id;
    }
    
    public void timeForNewTarget(boolean time) {
        timeForNewTarget = time;
    }
    
    public void getTarget() {
        
    }
    
    public void move() {
        
    }
}
