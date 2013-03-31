package settii.actorManager.weapons;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Merioksan Mikko
 */
public abstract class Weapon {
    protected int damage;
    protected float rateOfFire;
    protected long timeBetweenShots, timeSinceLastShot;
    protected String bullet;
    protected float range;
    
    protected long actor;
    
    public Weapon(long a, int d, float rate) {
        damage = d;
        rateOfFire = rate;
        timeBetweenShots = (long)(1000 / rate);
        timeSinceLastShot = timeBetweenShots-1;
        range = 200;
        
        actor = a;
        bullet = "assets/data/actors/bullet.xml";
    }
    public Weapon(long a) {
        this(a, 5, 0.5f);
    }
    
    public int getDamage() {
        return damage;
    }
    public void setDamage(int d) {
        damage = d;
    }
    
    public float getRateOfFire() {
        return rateOfFire;
    }
    public void setRateOfFire(float r) {
        rateOfFire = r;
    }
    
    public String getBullet() {
        return bullet;
    }
    public void setBullet(String b) {
        bullet = b;
    }
    
    public float getRange() {
        return range;
    }
    public void setRange(float r) {
        range = r;
    }
    
    public boolean ready() {
        return timeSinceLastShot >= timeBetweenShots;
    }
    
    public abstract String getName();
    public abstract void fire();
    
    public void update(long deltaMs) {
        timeSinceLastShot += deltaMs;
    }
    
    public void createFromXML(NodeList attributes) {
        Node n = attributes.item(0);
        while(n != null) {
            if(n.getNodeType() == Node.ELEMENT_NODE) {
                if(n.getNodeName().equals("rateOfFire")) {
                    rateOfFire = Float.parseFloat(n.getFirstChild().getNodeValue());
                    timeBetweenShots = (long)(1000 / rateOfFire);
                    timeSinceLastShot = timeBetweenShots-1;
                }
                else if(n.getNodeName().equals("damage")) {
                    damage = Integer.parseInt(n.getFirstChild().getNodeValue());
                }
                else if(n.getNodeName().equals("bullet")) {
                    bullet = n.getFirstChild().getNodeValue();
                }
                else if(n.getNodeName().equals("range")) {
                    range = Float.parseFloat(n.getFirstChild().getNodeValue());
                }
            }
            n = n.getNextSibling();
        }
    }
    
    public void copyTo(Weapon w) {
        w.setBullet(bullet);
        w.setDamage(damage);
        w.setRange(range);
        w.setRateOfFire(rateOfFire);
    }
}
