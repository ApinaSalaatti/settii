package settii.logic.sectors;

import java.util.ArrayList;
import java.util.Collections;
import org.lwjgl.opengl.Display;
import settii.Application;
import settii.eventManager.events.NewMessageEvent;
import settii.logic.messaging.Message;

/**
 *
 * @author Merioksan Mikko
 */
public abstract class GameSector {
    private long ID;
    private String name;
    
    
    protected ArrayList<EnemyLocation> enemyLocations;
    protected int freeLocations;
    protected EnemyLocation bossLocation;
    
    // coordinates of the upper left corner of this sector (for camera centering)
    protected float x, y;
    
    // the angle that if "up" at this sector, i.e. where do the bad guys come from.
    protected float upAngle;
    
    public GameSector(long id, float X, float Y) {
        ID = id;
        x = X;
        y = Y;
        
        enemyLocations = new ArrayList<EnemyLocation>();
        bossLocation = new EnemyLocation(x + Display.getWidth() / 2, y + Display.getHeight() / 2);
    }
    
    protected void addEnemyLocation(EnemyLocation el) {
        enemyLocations.add(el);
        freeLocations++;
    }
    protected void addEnemyLocation(float x, float y) {
        addEnemyLocation(new EnemyLocation(x, y));
    }
    
    public long getID() {
        return ID;
    }
    
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    
    public void setAngle(float a) {
        upAngle = a;
    }
    public float getAngle() {
        return upAngle;
    }
    
    public void setName(String n) {
        name = n;
    }
    public String getName() {
        return name;
    }
    
    public ArrayList<EnemyLocation> getEnemyLocations() {
        return enemyLocations;
    }
    public EnemyLocation getBossLocation() {
        return bossLocation;
    }
    
    public boolean free() {
        return freeLocations > 0;
    }
    
    public EnemyLocation reserveLocation(long actor) {
        if(!free()) {
            return null;
        }
        
        Collections.shuffle(enemyLocations);
        for(EnemyLocation loc : enemyLocations) {
            if(loc.reserve(actor)) {
                freeLocations--;
                return loc;
            }
        }
        
        return null;
    }
    public boolean releaseLocation(long actor) {
        for(EnemyLocation loc : enemyLocations) {
            if(loc.release(actor)) {
                freeLocations++;
                return true;
            }
        }
        
        return false;
    }
    
    public abstract void update(long deltaMs);
    
    public void spawnEnemy() {
        spawnEnemy("assets/data/actors/enemy.xml");
    }
    
    public abstract void spawnEnemy(String enemyData);
    
    public long spawnBoss(String bossData) {
        Application.get().getEventManager().queueEvent(new NewMessageEvent(new Message("Boss approaching", "Uh oh! Something really big is coming in from the " + name + "!", true)));
        return -1;
    }
}
