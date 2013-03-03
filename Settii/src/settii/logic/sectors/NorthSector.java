package settii.logic.sectors;

import org.lwjgl.opengl.Display;
import settii.utils.MathUtil;
/**
 *
 * @author Merioksan Mikko
 */
public class NorthSector extends GameSector {
    public static long ID;

    public NorthSector(long id) {
        super(id, Display.getWidth(), 0);
        super.setAngle(MathUtil.ANGLE_STRAIGHT_UP);
        super.setName("North sector");
        ID = id;
    }
    
    @Override
    public void update(long deltaMs) {
        
    }
    
    @Override
    public void spawnEnemy() {
        
    }
}
