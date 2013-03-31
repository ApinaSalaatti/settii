package settii.actorManager.ai;

import org.w3c.dom.NodeList;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
import settii.actorManager.components.WeaponsComponent;
import settii.eventManager.events.FireWeaponEvent;
import settii.logic.sectors.EnemyLocation;

/**
 *
 * @author Merioksan Mikko
 */
public class EnemySlaveAI extends BaseAI {
    private long currentSector;
    //private int currentLocation;
    
    // movement stuff...
    protected float targetX, targetY;
    boolean timeForNewTarget;
    boolean moving;
    
    // targeting stuff...
    private long targetID;
    
    public EnemySlaveAI(long owner) {
        super(owner);
        currentSector = -1;
        //currentLocation = -1;
        targetX = -1;
        targetY = -1;
        timeForNewTarget = true;
        moving = false;
    }
    
    public void setSector(long id) {
        currentSector = id;
    }
    public long getSector() {
        return currentSector;
    }
    
    @Override
    public void update(long deltaMs) {
        if(!moving) {
            targetAndShoot();
        }
    }
    
    public void setTarget(long id) {
        targetID = id;
    }
    
    public void getMoveTarget() {
        if(Application.get().getLogic().getGame().getSector(currentSector) != null) {
            EnemyLocation loc = Application.get().getLogic().getGame().getSector(currentSector).reserveLocation(owner);
            if(loc != null) {
                targetX = loc.x();
                targetY = loc.y();
                timeForNewTarget = false;
            }
        }
    }
    
    public void startMove() {
        PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
        if(ownPC != null) {
            moving = true;
            ownPC.setTarget(targetX, targetY);
        }
    }
    
    public void move() {
        PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
        if(ownPC.getAngleRad() < ownPC.getTargetAngle() + 0.1 && ownPC.getAngleRad() > ownPC.getTargetAngle() - 0.1) {
            ownPC.applyAcceleration(1.0f);
        }
    }
    
    public void stop() {
        PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
        ownPC.applyAcceleration(-1.0f);
    }
    
    public GameActor getTargetedActor() {
        return Application.get().getLogic().getActor(targetID);
    }
    
    public void targetAndShoot() {
        setTarget(Application.get().getLogic().getGame().getBaseID());
        target();
    }
    
    public void target() {
        GameActor t = getTargetedActor();
        if(t != null) {
            PhysicsComponent tPC = (PhysicsComponent)t.getComponent("PhysicsComponent");
            if(tPC != null) {
                PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
                if(ownPC != null) {
                    if(ownPC.getTargetX() != tPC.getX() || ownPC.getTargetY() != tPC.getY()) {
                        ownPC.setTarget(tPC.getX(), tPC.getY());
                    }
                    if(ownPC.getAngleRad() < ownPC.getTargetAngle() + 0.1 && ownPC.getAngleRad() > ownPC.getTargetAngle() - 0.1) {
                        WeaponsComponent wc = (WeaponsComponent)getOwningActor().getComponent("WeaponsComponent");
                        if(wc != null) {
                            if(wc.ready()) {
                                Application.get().getEventManager().queueEvent(new FireWeaponEvent(owner));
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void destroy() {
        if(currentSector > -1) {
            Application.get().getLogic().getGame().getSector(currentSector).releaseLocation(owner);
        }
    }
    
    @Override
    public void createFromXML(NodeList attributes) {

    }
}
