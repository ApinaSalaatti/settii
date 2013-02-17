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
    
    public AIComponent() {
        
    }
    
    @Override
    public String getName() {
        return "AIComponent";
    }
    
    @Override
    public void update(long deltaMs) {
        long t = -1;
        if(Application.get().getLogic().getGame().getPlayerWeapons().size() > 0) {
            t = Application.get().getLogic().getGame().getPlayerWeapons().get(0);
        }
        GameActor target = Application.get().getLogic().getActor(t);
        if(target != null) {
            PhysicsComponent tPC = (PhysicsComponent)target.getComponent("PhysicsComponent");
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
                        //ownPC.applyAcceleration(0.2f);
                    }
                }
            }
        }
        
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        
    }
}
