package settii.actorManager.weapons;

/**
 *
 * @author Merioksan Mikko
 */
public class HormoneBlaster extends Weapon {
    
    public HormoneBlaster(long a, int d, float r) {
        super(a, d, r);
    }
    public HormoneBlaster(long a) {
        this(a, 5, 0.5f);
    }
    
    @Override
    public String getName() {
        return "Hormone Blaster";
    }
    
    @Override
    public void fire() {
        
    }
}
