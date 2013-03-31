package settii.actorManager.ai;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
import settii.actorManager.components.StatusComponent;
import settii.actorManager.components.WeaponsComponent;
import settii.eventManager.events.FireWeaponEvent;
import settii.utils.MathUtil;

/**
 *
 * @author Merioksan Mikko
 */
public class HumanBasicWeaponAI extends BaseAI {
    //private Targeting targeting;
    private float guardAngle;
    
    // targeting stuff...
    private final static int FIRE_AT_ACTOR_MODE = 1;
    private final static int FIRE_AT_LOCATION_MODE = 2;
    private long targetID;
    private float attackTargetX, attackTargetY;
    private int firingMode;
    
    public HumanBasicWeaponAI(long owner) {
        super(owner);
        guardAngle = -1;
        targetID = -1;
        
        firingMode = FIRE_AT_ACTOR_MODE;
    }
    /*
    private void setTargeting(String t) {
        if(t.equals("EnemyBasic")) {
            targeting = new EnemyBasicTargeting(owner);
        }
        else if(t.equals("Area")) {
            targeting = new AreaTargeting(owner);
        }
    }
    public void setTargeting(Targeting t) {
        targeting = t;
    }
    public Targeting getTargeting() {
        return targeting;
    }
    
    @Override
    public void update(long deltaMs) {
        if(targeting != null) {
            targeting.update(deltaMs);
        }
    }
    */
    
    /**
     * Set a target enemy that we will fire at.
     * 
     * @param id the id of the target actor
     */
    public void setTarget(long id) {
        targetID = id;
        firingMode = FIRE_AT_ACTOR_MODE;
    }
    /*
     * Set a target point that we will fire at.
     */
    public void setTarget(float x, float y) {
        attackTargetX = x;
        attackTargetY = y;
        firingMode = FIRE_AT_LOCATION_MODE;
    }
    
    public GameActor getTargetedActor() {
        return Application.get().getLogic().getActor(targetID);
    }
    
    public void setGuardAngle(float angle) {
        if(angle < 0) {
            guardAngle = angle + MathUtil.PI * 2;
        }
        else {
            guardAngle = angle;
        }
    }
    
    @Override
    public void update(long deltaMs) {
        switch(firingMode) {
            case FIRE_AT_ACTOR_MODE:
                targetActor();
                break;
            case FIRE_AT_LOCATION_MODE:
                targetLocation();
                break;
        }
    }
    
    private boolean withinRadius(GameActor enemy) {
        GameActor a = getOwningActor();
        PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        WeaponsComponent wc = (WeaponsComponent)a.getComponent("WeaponsComponent");
        PhysicsComponent enemyPC = (PhysicsComponent)enemy.getComponent("PhysicsComponent");
        
        float cx = pc.getX();
        float cy = pc.getY();
        float range = wc.getRange();
        
        float relativeX = Math.abs(enemyPC.getX() - cx);
        float relativeY = Math.abs(enemyPC.getY() - cy);
        
        return withinRadius(relativeX, relativeY, range);
    }
    private boolean withinRadius(float distX, float distY, float radius) {
        return (distX * distX + distY * distY) <= (radius * radius);
    }
    
    public void targetActor() {
        GameActor t = getTargetedActor();
        if(t != null && withinRadius(t)) {
            PhysicsComponent tPC = (PhysicsComponent)t.getComponent("PhysicsComponent");
            if(tPC != null) {
                PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
                if(ownPC != null) {
                    ownPC.setTarget(tPC.getX(), tPC.getY());
                    if(ownPC.getAngleRad() < ownPC.getTargetAngle() + 0.1 && ownPC.getAngleRad() > ownPC.getTargetAngle() - 0.1) {
                        WeaponsComponent wc = (WeaponsComponent)getOwningActor().getComponent("WeaponsComponent");
                        if(wc != null && wc.ready()) {
                            Application.get().getEventManager().queueEvent(new FireWeaponEvent(owner));
                        }
                    }
                }
            }
        }
        else if(guardAngle >= 0) {
            PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
            ownPC.setTargetAngle(guardAngle);
        }
    }
    
    public void targetLocation() {
        PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
        WeaponsComponent wc = (WeaponsComponent)getOwningActor().getComponent("WeaponsComponent");
        
        if(ownPC != null && withinRadius(Math.abs(ownPC.getX() - attackTargetX), Math.abs(ownPC.getY() - attackTargetY), wc.getRange())) {
            ownPC.setTarget(attackTargetX, attackTargetY);

            if(ownPC.getAngleRad() < ownPC.getTargetAngle() + 0.1 && ownPC.getAngleRad() > ownPC.getTargetAngle() - 0.1) {
                if(wc != null) {
                    Application.get().getEventManager().queueEvent(new FireWeaponEvent(owner));
                }
            }
        }        
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        
    }
}
