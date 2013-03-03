package settii.logic.sectors;

import org.lwjgl.opengl.Display;
import settii.utils.MathUtil;
/**
 *
 * @author Merioksan Mikko
 */
public class SouthSector extends GameSector {
    public static long ID;
    
    public SouthSector(long id) {
        super(id, Display.getWidth(), Display.getHeight() * 2);
        super.setAngle(MathUtil.ANGLE_STRAIGHT_DOWN);
        super.setName("South sector");
        ID = id;
    }
    
    @Override
    public void update(long deltaMs) {
        
    }
    
    @Override
    public void spawnEnemy() {
        
    }
}
