package settii.actorManager.weapons;

import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
import settii.actorManager.components.StatusComponent;
import settii.eventManager.events.WeaponFiredEvent;

/**
 *
 * @author Merioksan Mikko
 */
public class RubberLauncher extends Weapon {

    public RubberLauncher(long a, int d, float r) {
        super(a, d, r);
    }
    public RubberLauncher(long a) {
        this(a, 5, 0.5f);
    }
    
    @Override
    public String getName() {
        return "Rubber Launcher";
    }
    
    @Override
    public void fire() {
        if(timeSinceLastShot >= timeBetweenShots) {
            GameActor a = Application.get().getLogic().getActor(actor);
            //WeaponsComponent wc1 = (WeaponsComponent)a.getComponent("WeaponsComponent");
            PhysicsComponent pc1 = (PhysicsComponent)a.getComponent("PhysicsComponent");
            StatusComponent sc1 = (StatusComponent)a.getComponent("StatusComponent");

            GameActor bul = Application.get().getLogic().createActor(bullet);

            float weaponDist = pc1.getHeight() / 2 + 10; // barrel of the gun is in front of the weapon

            // calculate correct spot with angle of the actor
            float bulletY = pc1.getY() - weaponDist * (float)Math.sin(pc1.getAngleRad());
            float bulletX = pc1.getX() - weaponDist * (float)Math.cos(pc1.getAngleRad());
            bul.move(bulletX, bulletY);

            PhysicsComponent pc2 = (PhysicsComponent)bul.getComponent("PhysicsComponent");
            StatusComponent sc2 = (StatusComponent)bul.getComponent("StatusComponent");
            // the projectile must know who shot it to prevent friendly fire
            sc2.setAllegiance(sc1.getAllegiance());

            pc2.setDamage(damage);
            pc2.setAngleRad(pc1.getAngleRad());
            pc2.setLifetime((long)(((range - weaponDist) * 1000) /  pc2.getMaxSpeed()));
            pc2.applyAcceleration(1.0f);

            Application.get().getEventManager().queueEvent(new WeaponFiredEvent(actor));
            timeSinceLastShot = 0;
        }
    }
}
