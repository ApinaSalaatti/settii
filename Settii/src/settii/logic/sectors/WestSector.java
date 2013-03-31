package settii.logic.sectors;

import org.lwjgl.opengl.Display;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.ai.BaseAI;
import settii.actorManager.ai.EnemyBasicAI;
import settii.actorManager.ai.EnemyBossAI;
import settii.actorManager.components.AIComponent;
import settii.actorManager.components.PhysicsComponent;
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
        
        addEnemyLocation(new EnemyLocation(getX() + 200, getY() + 300));
    }
    
    @Override
    public void update(long deltaMs) {
        
    }
    
    @Override
    public void spawnEnemy(String enemyData) {
        if(!free()) {
            return;
        }
        int r = Application.get().getRNG().nextInt(6);
        GameActor a = Application.get().getLogic().createActor(enemyData);
        PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        pc.setLocation(x - 100, y + 50 + 100 * r);
        pc.setAngleRad(MathUtil.getOppositeAngleRad(upAngle));
        pc.setTargetAngle(pc.getAngleRad());
        
        AIComponent aic = (AIComponent)a.getComponent("AIComponent");
        BaseAI ai = aic.getAI();
        if(ai instanceof EnemyBasicAI) {
            EnemyBasicAI ebai = (EnemyBasicAI)ai;
            ebai.setSector(ID);
        }
    }
    
    @Override
    public long spawnBoss(String bossData) {
        super.spawnBoss(bossData);
        GameActor a = Application.get().getLogic().createActor(bossData);
        PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        pc.setLocation(x - 200, y + 200);
        pc.setAngleRad(MathUtil.getOppositeAngleRad(upAngle));
        pc.setTargetAngle(pc.getAngleRad());
        
        AIComponent aic = (AIComponent)a.getComponent("AIComponent");
        BaseAI ai = aic.getAI();
        if(ai instanceof EnemyBossAI) {
            EnemyBossAI ebai = (EnemyBossAI)ai;
            ebai.setSector(ID);
        }
        
        return a.getID();
    }
}
