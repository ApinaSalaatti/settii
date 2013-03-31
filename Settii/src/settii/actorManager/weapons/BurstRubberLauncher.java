package settii.actorManager.weapons;

import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
import settii.actorManager.components.StatusComponent;
import settii.actorManager.components.WeaponsComponent;
import settii.eventManager.events.WeaponFiredEvent;

/**
 *
 * @author Merioksan Mikko
 */
public class BurstRubberLauncher extends Weapon {
    private int burst, fired;
    private long delay, elapsed;
    private boolean firing;
    
    public BurstRubberLauncher(long a, int d, float r, int b) {
        super(a, d, r);
        burst = b;
        fired = 0;
        delay = 100;
        elapsed = 0;
        firing = false;
    }
    public BurstRubberLauncher(long a) {
        this(a, 5, 0.5f, 3);
    }
    
    @Override
    public String getName() {
        return "Rubber Launcher";
    }
    
    @Override
    public void update(long deltaMs) {
        super.update(deltaMs);
        
        if(firing) {
            elapsed += deltaMs;
            if(elapsed >= delay) {
                fireOnce();
                fired++;
                elapsed = 0;
                if(fired >= burst) {
                    firing = false;
                    fired = 0;
                }
            }
        }
    }
    
    @Override
    public void fire() {
        if(timeSinceLastShot > timeBetweenShots) {
            firing = true;
            timeSinceLastShot = 0;
        }
    }
    
    public void fireOnce() {
        GameActor a = Application.get().getLogic().getActor(actor);
        WeaponsComponent wc1 = (WeaponsComponent)a.getComponent("WeaponsComponent");
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
    }
}
