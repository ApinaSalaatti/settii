package settii.logic;

import settii.Application;

/**
 *
 * @author Merioksan Mikko
 */
public class WeaponLocation {
    public static long NO_WEAPON = -1;
    private float x, y;
    private long weaponID;
    
    public WeaponLocation(float x, float y) {
        this.x = x;
        this.y = y;
        weaponID = NO_WEAPON;
    }
    
    public boolean taken() {
        return weaponID != NO_WEAPON;
    }
    
    public boolean take(long id) {
        if(weaponID != NO_WEAPON) {
            return false;
        }
        
        weaponID = id;
        return true;
    } 
    public boolean release(long id) {
        if(weaponID == id) {
            weaponID = NO_WEAPON;
            return true;
        }
        
        return false;
    }
    
    public long getWeaponID() {
        return weaponID;
    }
    
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    
    public void update(long deltaMs) {
        if(Application.get().getLogic().getActor(weaponID) == null) {
            weaponID = NO_WEAPON;
        }
    }
}
