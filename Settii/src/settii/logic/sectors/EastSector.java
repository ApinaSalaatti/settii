package settii.logic.sectors;

import org.lwjgl.opengl.Display;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
import settii.utils.MathUtil;
/**
 *
 * @author Merioksan Mikko
 */
public class EastSector extends GameSector {
    public static long ID;
    
    public EastSector(long id) {
        super(id, Display.getWidth() * 2, Display.getHeight());
        super.setAngle(MathUtil.ANGLE_STRAIGHT_RIGHT);
        super.setName("East sector");
        ID = id;
    }
    
    @Override
    public void update(long deltaMs) {
        
    }
    
    @Override
    public void spawnEnemy() {
        //int r = Application.get().getRNG().nextInt(Display.getWidth() - 100) + 50;
        long id = Application.get().getLogic().createActor("assets/data/actors/enemy.xml");
        GameActor a = Application.get().getLogic().getActor(id);
        PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        pc.setLocation(x + Display.getWidth() - 100, y + 50);
        pc.setAngleRad(MathUtil.getOppositeAngleRad(upAngle));
    }
}
