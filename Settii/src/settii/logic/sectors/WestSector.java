package settii.logic.sectors;

import org.lwjgl.opengl.Display;
import settii.utils.MathUtil;
/**
 *
 * @author Merioksan Mikko
 */
public class WestSector extends GameSector {
    public static long ID;
    
    public WestSector(long id) {
        super(id, 0, Display.getHeight());
        super.setAngle(MathUtil.ANGLE_STRAIGHT_LEFT);
        super.setName("West sector");
        ID = id;
    }
    
    @Override
    public void update(long deltaMs) {
        
    }
    
    @Override
    public void spawnEnemy() {
        
    }
}
