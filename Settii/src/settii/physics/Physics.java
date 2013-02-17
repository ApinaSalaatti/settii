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
    
    public Physics(GameLogic logic) {
        mainLogic = logic;
        
        Application.get().getEventManager().register(ActorMovedEvent.eventType, new ActorMovedListener(this));
    }
    
    public boolean init() {
        return true;
    }
    
    public void update(long deltaMs) {
        
    }
    
    public boolean checkCollisions(long actor) {
        GameActor a1 = mainLogic.getActor(actor);
        if(a1 != null) {
            StatusComponent sc1 = (StatusComponent)a1.getComponent("StatusComponent");
            for(GameActor a2 : mainLogic.getActors()) {
                StatusComponent sc2 = (StatusComponent)a2.getComponent("StatusComponent");
                
                // no friendly fire
                if(!sc1.getAlleciange().equals(sc2.getAlleciange())) {
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
    
    public boolean checkProjectileCollision(GameActor proj, GameActor other) {
        // currently we are only interested in collision between a projectile and another type of actor
        if(proj == null || other == null) {
            return false;
        }
        PhysicsComponent projPC = (PhysicsComponent)proj.getComponent("PhysicsComponent");
        PhysicsComponent otherPC = (PhysicsComponent)other.getComponent("PhysicsComponent");

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
        otherPC.takeDamage(projPC.getDamage());
        Application.get().getEventManager().queueEvent(new ActorDestroyedEvent(proj.getID())); // destroy the projectile
        return true;
    }
    
    public void actorMovedListener(long id) {
        checkCollisions(id);
    }
}
