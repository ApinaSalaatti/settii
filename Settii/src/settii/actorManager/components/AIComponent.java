/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.actorManager.components;

import settii.actorManager.GameActor;
import settii.Application;
import settii.actorManager.BaseComponent;
import settii.eventManager.events.FireWeaponEvent;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Merioksan Mikko
 */
public class AIComponent extends BaseComponent {
    private long target;
    private int targetY;
    
    public AIComponent() {
        target = -1;
        targetY = -1;
    }
    
    @Override
    public String getName() {
        return "AIComponent";
    }
    
    @Override
    public void update(long deltaMs) {
        PhysicsComponent ownPC = (PhysicsComponent)owner.getComponent("PhysicsComponent");
        if(targetY < 0) {
            targetY = Application.get().getRNG().nextInt(100) + 100;
        }
        if(ownPC.getY() < targetY) {
            moveDown();
        }
        else {
            if(ownPC.getSpeed() > 0) {
                stop();
            }
            else {
                targetAndShoot();
            }
        }
    }
    
    public void moveDown() {
        PhysicsComponent ownPC = (PhysicsComponent)owner.getComponent("PhysicsComponent");
        if(ownPC != null) {
            float targetX = ownPC.getX();
            float targetY = ownPC.getY() + 50;
            ownPC.setTarget(targetX, targetY);
            if(ownPC.getAngleRad() < ownPC.getTargetAngle() + 0.1 && ownPC.getAngleRad() > ownPC.getTargetAngle() - 0.1) {
                ownPC.applyAcceleration(0.02f);
            }
        }
    }
    
    public void stop() {
        PhysicsComponent ownPC = (PhysicsComponent)owner.getComponent("PhysicsComponent");
        ownPC.applyAcceleration(-1.0f);
    }
    
    public void targetAndShoot() {
        target = Application.get().getLogic().getGame().getBaseID();
        if(Application.get().getLogic().getGame().getBuildings().contains(target)) {
            target();
        }
        else if(Application.get().getLogic().getGame().getPlayerWeapons().contains(target)) {
            target();
        }
        else {
            if(Application.get().getLogic().getGame().getBuildings().size() > 0) {
                int t = Application.get().getRNG().nextInt(Application.get().getLogic().getGame().getBuildings().size());
                //target = Application.get().getLogic().getGame().getBuildings().get(t);
            }
            else if(Application.get().getLogic().getGame().getPlayerWeapons().size() > 0) {
                int t = Application.get().getRNG().nextInt(Application.get().getLogic().getGame().getPlayerWeapons().size());
                //target = Application.get().getLogic().getGame().getPlayerWeapons().get(t);
            }
            target();
        }
    }
    
    public void target() {
        GameActor t = Application.get().getLogic().getActor(target);
        if(t != null) {
            PhysicsComponent tPC = (PhysicsComponent)t.getComponent("PhysicsComponent");
            if(tPC != null) {
                PhysicsComponent ownPC = (PhysicsComponent)owner.getComponent("PhysicsComponent");
                if(ownPC != null) {
                    if(ownPC.getTargetX() != tPC.getX() || ownPC.getTargetY() != tPC.getY()) {
                        ownPC.setTarget(tPC.getX(), tPC.getY());
                    }
                    if(ownPC.getAngleRad() < ownPC.getTargetAngle() + 0.1 && ownPC.getAngleRad() > ownPC.getTargetAngle() - 0.1) {
                        WeaponsComponent wc = (WeaponsComponent)owner.getComponent("WeaponsComponent");
                        if(wc != null) {
                            Application.get().getEventManager().queueEvent(new FireWeaponEvent(owner.getID()));
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        
    }
}
