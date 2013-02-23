package settii.actorManager.components;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import settii.actorManager.BaseComponent;
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
        damage = 5; // default dmg, I dunno!
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
        for(int i = 0; i < attributes.getLength(); i++) {
            Node node = attributes.item(i);
            
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Node value = node.getFirstChild();
                if(node.getNodeName().equalsIgnoreCase("rateOfFire")) {
                    rateOfFire = Integer.parseInt(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("damage")) {
                    damage = Integer.parseInt(value.getNodeValue());
                }
                if(node.getNodeName().equalsIgnoreCase("bullet")) {
                    bullet = value.getNodeValue();
                }
            }
        }
    }
    
    public int getDamage() {
        return damage;
    }
    public void setDamage(int d) {
        damage = d;
    }
    
    public void setReady(boolean r) {
        readyToFire = r;
    }
    
    public boolean fire() {
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
            sc2.setAllegiance(sc1.getAllegiance());
            
            pc2.setDamage(damage);
            pc2.setAngleRad(pc1.getAngleRad());
            pc2.applyAcceleration(1.0f);
            timeSinceLastShot = 0;
            return true;
        }
        
        return false;
    }
    
    @Override
    public void update(long deltaMs) {
        timeSinceLastShot += deltaMs;
    }
    
    @Override
    public void copyTo(BaseComponent bc) {
        WeaponsComponent wc = (WeaponsComponent)bc;
        wc.setDamage(damage);
        wc.setOwner(owner);
    }
}
