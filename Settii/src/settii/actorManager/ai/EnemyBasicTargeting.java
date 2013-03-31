package settii.actorManager.ai;

import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
import settii.actorManager.components.WeaponsComponent;
import settii.eventManager.events.FireWeaponEvent;

/**
 *
 * @author Merioksan Mikko
 */
public class EnemyBasicTargeting extends Targeting {
    
    public EnemyBasicTargeting(long owner) {
        super(owner, "EnemyBasic");
    }
    @Override
    public void update(long deltaMs) {
        targetAndShoot();
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
                            Application.get().getEventManager().queueEvent(new FireWeaponEvent(owner));
                        }
                    }
                }
            }
        }
    }
}
