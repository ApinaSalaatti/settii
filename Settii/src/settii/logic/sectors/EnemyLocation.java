package settii.logic.sectors;

/**
 *
 * @author Merioksan Mikko
 */
public class EnemyLocation {
    public static long NO_ENEMY = -1;
    private float x, y;
    private long enemyID;
    
    public EnemyLocation(float X, float Y) {
        x = X;
        y = Y;
        enemyID = NO_ENEMY;
    }
    
    public float x() {
        return x;
    }
    public float y() {
        return y;
    }
    public boolean taken() {
        return enemyID != NO_ENEMY;
    }
    public void setEnemy(long id) {
        enemyID = id;
    }
    
    public boolean reserve(long id) {
        if(enemyID == NO_ENEMY) {
            enemyID = id;
            return true;
        }
        else {
            return false;
        }
    }
    public boolean release(long id) {
        if(enemyID == id) {
            enemyID = NO_ENEMY;
            return true;
        }
        else {
            return false;
        }
    }
}
