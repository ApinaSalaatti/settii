package settii.actorManager.components;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import settii.actorManager.BaseComponent;
import settii.Application;
import settii.actorManager.weapons.*;
import settii.eventManager.events.WeaponFiredEvent;
import settii.utils.MathUtil;
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
    private long chargeTime;
    private long charged;
    boolean charging;
    
    private float range, angleOfAttack;
    
    private Weapon weapon;
    
    public WeaponsComponent() {
        damage = 5; // default dmg, I dunno!
        bullet = "assets/data/actors/bullet.xml";
        rateOfFire = 1000;
        timeSinceLastShot = rateOfFire-1;
        
        // testing stuff...
        range = 200;
        angleOfAttack = MathUtil.degToRad(45);
        
        chargeTime = 400;
        charged = 0;
        charging = false;
    }
    
    @Override
    public String getName() {
        return "WeaponsComponent";
    }
    
    @Override
    public void createFromXML(NodeList attributes) {
        //weapon = new SpermGun(owner.getID()); // default weapons, for now atleast...
        for(int i = 0; i < attributes.getLength(); i++) {
            Node node = attributes.item(i);
            
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Node value = node.getFirstChild();
                if(node.getNodeName().equalsIgnoreCase("rateOfFire")) {
                    rateOfFire = Integer.parseInt(value.getNodeValue());
                    timeSinceLastShot = rateOfFire-1;
                }
                else if(node.getNodeName().equalsIgnoreCase("damage")) {
                    damage = Integer.parseInt(value.getNodeValue());
                }
                else if(node.getNodeName().equalsIgnoreCase("bullet")) {
                    bullet = value.getNodeValue();
                }
                else if(node.getNodeName().equals("weapon")) {
                    createWeapon(node);
                }
            }
        }
    }
    
    public void createWeapon(Node node) {
        Node n = node.getFirstChild();
        
        while(n != null) {
            if(n.getNodeType() == Node.ELEMENT_NODE) {
                selectWeapon(n.getNodeName());
                weapon.createFromXML(n.getChildNodes());
            }
            n = n.getNextSibling();
        }
    }
    
    private void selectWeapon(String w) {
        if(w.equalsIgnoreCase("SpermGun")) {
            weapon = new SpermGun(owner.getID());
        }
        else if(w.equalsIgnoreCase("BurstRubberLauncher")) {
            weapon = new BurstRubberLauncher(owner.getID());
        }
        else if(w.equalsIgnoreCase("RubberLauncher")) {
            weapon = new RubberLauncher(owner.getID());
        }
        else if(w.equalsIgnoreCase("HormoneBlaster")) {
            weapon = new HormoneBlaster(owner.getID());
        }
    }
    
    public int getDamage() {
        return weapon.getDamage();
    }
    public void setDamage(int d) {
        weapon.setDamage(d);
    }
    
    public float getRange() {
        return weapon.getRange();
    }
    public void setRange(float r) {
        weapon.setRange(r);
    }
    
    public float getAttackAngle() {
        return angleOfAttack;
    }
    public void setAttackAngleRad(float a) {
        angleOfAttack = a;
    }
    
    public void setReady(boolean r) {
        readyToFire = r;
    }
    public boolean ready() {
        return weapon.ready();
    }
    
    public boolean charging() {
        return charging;
    }
    
    public String getBullet() {
        return weapon.getBullet();
    }
    
    public Weapon getWeapon() {
        return weapon;
    }
    
    public boolean fire() {
        if(readyToFire && weapon.ready()) {
            charging = true;
            return true;
        }
        
        return false;
    }
    
    public void charge(long deltaMs) {
        charged += deltaMs;
        
        if(charged >= chargeTime) {
            timeSinceLastShot = 0;
            charged = 0;
            charging = false;
            weapon.fire();
        }
    }
    
    @Override
    public void update(long deltaMs) {
        timeSinceLastShot += deltaMs;
        
        if(charging) {
            charge(deltaMs);
        }
        
        if(weapon != null) {
            weapon.update(deltaMs);
        }
    }
    
    @Override
    public void copyTo(BaseComponent bc) {
        WeaponsComponent wc = (WeaponsComponent)bc;
        if(weapon != null) {
            weapon.copyTo(wc.getWeapon());
        }
    }
}
