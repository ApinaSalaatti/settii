package settii.physics;

import settii.Application;
import settii.eventManager.events.*;
import settii.physics.listeners.*;
import settii.logic.GameLogic;
import settii.actorManager.GameActor;
import settii.actorManager.components.*;
import java.awt.Rectangle;
/**
 *
 * @author Merioksan Mikko
 */
public class Physics {
    private GameLogic mainLogic;
    private int timesChecked;
    public Physics(GameLogic logic) {
        mainLogic = logic;
        timesChecked = 0;
        Application.get().getEventManager().register(ActorMovedEvent.eventType, new ActorMovedListener(this));
    }
    
    public boolean init() {
        return true;
    }
    
    public void update(long deltaMs) {
        
    }
    
    public boolean checkCollisions(long actor) {
        timesChecked++;
        GameActor a1 = mainLogic.getActor(actor);
        if(a1 == null || !a1.isEnabled()) {
            return false;
        }
        
        StatusComponent sc1 = (StatusComponent)a1.getComponent("StatusComponent");
        for(GameActor a2 : mainLogic.getActors()) {
            if(a2.isEnabled()) {
                StatusComponent sc2 = (StatusComponent)a2.getComponent("StatusComponent");

                // no friendly fire
                if(!sc1.getAllegiance().equals(sc2.getAllegiance())) {
                    GameActor proj = null;
                    GameActor other = null;

                    if(sc1.getType().equals("projectile") && !sc2.getType().equals("projectile")) {
                        proj = a1;
                        other = a2;
                    }
                    else if(!sc1.getType().equals("projectile") && sc2.getType().equals("projectile")) {
                        proj = a2;
                        other = a1;
                    }

                    checkProjectileCollision(proj, other);
                }
            }
        }
        
        return false;
    }
    
    // TODO: make the hitboxes turn with the actors. (better yet: do all this shit again from the ground up)
    public boolean checkProjectileCollision(GameActor proj, GameActor other) {
        // currently we are only interested in collision between a projectile and another type of actor
        if(proj == null || other == null) {
            return false;
        }
        PhysicsComponent projPC = (PhysicsComponent)proj.getComponent("PhysicsComponent");
        PhysicsComponent otherPC = (PhysicsComponent)other.getComponent("PhysicsComponent");
        
        if(projPC == null || otherPC == null) {
            return false;
        }
        
        Rectangle projHb = projPC.getHitbox();
        Rectangle otherHb = otherPC.getHitbox();

        if(projHb.x + projHb.width < otherHb.x) {
            return false;
        }
        if(projHb.x > otherHb.x + otherHb.width) {
            return false;
        }
        if(projHb.y + projHb.height < otherHb.y) {
            return false;
        }
        if(projHb.y > otherHb.y + otherHb.height) {
            return false;
        }
        
        // we got here, we got a HIT!
        Application.get().getEventManager().queueEvent(new CollisionEvent(proj, other));
        otherPC.takeDamage(projPC.getDamage());
        /*
        // TODO: figure something better out here (or rather somewhere else...)
        if(otherPC.takeDamage(projPC.getDamage())) {
            InventoryComponent giver = (InventoryComponent)other.getComponent("InventoryComponent");
            StatusComponent sc = (StatusComponent)proj.getComponent("StatusComponent");
            
            if(sc.getAlleciange().equals("friendly")) {
                GameActor p = Application.get().getLogic().getActor(Application.get().getHumanView().getAttachedActor());
                InventoryComponent getter = (InventoryComponent)p.getComponent("InventoryComponent");
                getter.addMoney(giver.getMoney());
            }
        }
        * 
        */
        Application.get().getEventManager().executeEvent(new ActorDestroyedEvent(proj)); // destroy the projectile
        return true;
    }
    
    public void actorMovedListener(long id) {
        checkCollisions(id);
    }
}
