package settii.actorManager.components;

import org.w3c.dom.NodeList;
import settii.actorManager.BaseComponent;
import settii.actorManager.components.PhysicsComponent;
import settii.Application;
import settii.actorManager.GameActor;
/**
 *
 * @author Merioksan Mikko
 */
public class WeaponsComponent extends BaseComponent {
    private int damage;
    private int rateOfFire;
    private long timeSinceLastShot;
    private String bullet;
    
    private boolean readyToFire;
    
    public WeaponsComponent() {
        damage = 5;
        bullet = "assets/data/actors/bullet.xml";
        rateOfFire = 100;
        timeSinceLastShot = 1000;
    }
    
    @Override
    public String getName() {
        return "WeaponsComponent";
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        
    }
    
    public void setReady(boolean r) {
        readyToFire = r;
    }
    
    public void fire() {
        if(readyToFire && timeSinceLastShot >= rateOfFire) {
            PhysicsComponent pc1 = (PhysicsComponent)owner.getComponent("PhysicsComponent");
            StatusComponent sc1 = (StatusComponent)owner.getComponent("StatusComponent");

            long id = Application.get().getLogic().createActor(bullet);
            GameActor bul = Application.get().getLogic().getActor(id);
            

            float weaponDist = pc1.getHeight() / 2 + 10; // barrel of the gun is in front of the weapon
            
            // calculate correct spot with angle of the actor
            float bulletY = pc1.getY() - weaponDist * (float)Math.sin(pc1.getAngleRad());
            float bulletX = pc1.getX() - weaponDist * (float)Math.cos(pc1.getAngleRad());
            bul.move(bulletX, bulletY);
            
            PhysicsComponent pc2 = (PhysicsComponent)bul.getComponent("PhysicsComponent");
            StatusComponent sc2 = (StatusComponent)bul.getComponent("StatusComponent");
            
            // the projectile must know who shot it to prevent friendly fire
            sc2.setAllegiance(sc1.getAlleciange());
            
            pc2.setAngleRad(pc1.getAngleRad());
            pc2.applyAcceleration(1.0f);
            timeSinceLastShot = 0;
        }
    }
    
    @Override
    public void update(long deltaMs) {
        timeSinceLastShot += deltaMs;
    }
}
