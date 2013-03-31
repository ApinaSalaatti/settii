package settii.actorManager.ai;

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
public class AreaTargeting extends Targeting {
    private float guardAngle;
    
    public AreaTargeting(long owner) {
        super(owner, "Area");
        guardAngle = -1; // first set an invalid guarding angle
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
        if(getTargetedActor() == null)
            checkEnemies();
        
        target();
    }
    
    private void checkEnemies() {
        GameActor a = getOwningActor();
        PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        WeaponsComponent wc = (WeaponsComponent)a.getComponent("WeaponsComponent");
        
        float cx = pc.getX();
        float cy = pc.getY();
        
        float attackAngle = wc.getAttackAngle();
        float range = wc.getRange();
        
        float facingAngle = pc.getTargetAngle();
        float angleToFirstEdge = -attackAngle - facingAngle;
        float angleToSecondEdge = attackAngle - facingAngle;
        float firstEdgeX = range * (float)Math.cos(angleToFirstEdge);
        float firstEdgeY = range * (float)Math.sin(angleToFirstEdge);
        float secondEdgeX = range * (float)Math.cos(angleToSecondEdge);
        float secondEdgeY = range * (float)Math.sin(angleToSecondEdge);
        
        for(GameActor enemy : Application.get().getLogic().getActors()) {
            StatusComponent enemySC = (StatusComponent)enemy.getComponent("StatusComponent");
            StatusComponent ownSC = (StatusComponent)getOwningActor().getComponent("StatusComponent");
            if(!enemySC.getAllegiance().equals(ownSC.getAllegiance()) && !enemySC.getType().equalsIgnoreCase("Projectile")) { // only shoot enemies. :)
                PhysicsComponent enemyPC = (PhysicsComponent)enemy.getComponent("PhysicsComponent");
                float relativeX = Math.abs(enemyPC.getX() - cx);
                float relativeY = Math.abs(enemyPC.getY() - cy);

                if(withinRadius(relativeX, relativeY, range) && isClockwise(firstEdgeX, firstEdgeY, relativeX, relativeY) && !isClockwise(secondEdgeX, secondEdgeY, relativeX, relativeY)) {
                    setTarget(enemy.getID());
                    return;
                }
            }
        }
        // no target within radius
        setTarget(-1);
    }
    
    private boolean withinRadius(float distX, float distY, float radius) {
        return (distX * distX + distY * distY) <= (radius * radius);
    }
    
    private boolean isClockwise(float edgeX, float edgeY, float pointX, float pointY) {
        return -edgeX * pointY + pointX * edgeY > 0;
    }
    
    public void target() {
        GameActor t = getTargetedActor();
        if(t != null) {
            PhysicsComponent tPC = (PhysicsComponent)t.getComponent("PhysicsComponent");
            if(tPC != null) {
                PhysicsComponent ownPC = (PhysicsComponent)getOwningActor().getComponent("PhysicsComponent");
                if(ownPC != null) {
                    ownPC.setTarget(tPC.getX(), tPC.getY());
                    if(ownPC.getAngleRad() < ownPC.getTargetAngle() + 0.1 && ownPC.getAngleRad() > ownPC.getTargetAngle() - 0.1) {
                        WeaponsComponent wc = (WeaponsComponent)getOwningActor().getComponent("WeaponsComponent");
                        if(wc != null) {
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
}
