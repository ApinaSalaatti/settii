package settii.logic.sectors;

/**
 *
 * @author Merioksan Mikko
 */
public abstract class GameSector {
    private long ID;
    private String name;
    
    // coordinates of the upper left corner of this sector (for camera centering)
    protected float x, y;
    
    // the angle that if "up" at this sector, i.e. where do the bad guys come from.
    protected float upAngle;
    
    public GameSector(long id, float X, float Y) {
        ID = id;
        x = X;
        y = Y;
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
    
    public abstract void update(long deltaMs);
    
    public abstract void spawnEnemy();
}
