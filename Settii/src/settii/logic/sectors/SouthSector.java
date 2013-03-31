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
public class SouthSector extends GameSector {
    public static long ID;
    
    public SouthSector(long id) {
        super(id, Display.getWidth(), Display.getHeight() * 2);
        super.setAngle(MathUtil.ANGLE_STRAIGHT_DOWN);
        super.setName("South sector");
        ID = id;
        
        addEnemyLocation(new EnemyLocation(getX() + 100, getY() + Display.getHeight() - 300));
    }
    
    @Override
    public void update(long deltaMs) {
        
    }
    
    @Override
    public void spawnEnemy(String enemyData) {
        if(!free()) {
            return;
        }
        int r = Application.get().getRNG().nextInt(7) ;
        GameActor a = Application.get().getLogic().createActor(enemyData);
        PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        pc.setLocation(x + 50 + r * 100, y + Display.getHeight() + 50);
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
        pc.setLocation(x + 100, y + Display.getHeight() + 200);
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
