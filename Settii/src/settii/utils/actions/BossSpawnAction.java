package settii.utils.actions;

import settii.Application;
import settii.logic.EnemyManager;
import settii.logic.sectors.GameSector;

/**
 *
 * @author Merioksan Mikko
 */
public class BossSpawnAction extends Action {
    private long bossID;
    public boolean spawned, dead;
    private EnemyManager manager;
    
    public BossSpawnAction(EnemyManager em) {
        manager = em;
        spawned = false;
        dead = false;
        bossID = -1;
    }
    
    private void spawnBoss() {
        int r = Application.get().getRNG().nextInt(4) + 1;
        GameSector sector = Application.get().getLogic().getGame().getSector((long)r);
        if(sector.free()) {
            bossID = sector.spawnBoss(manager.getNextBoss());
            spawned = true;
        }
    }
    
    @Override
    public void update(long deltaMs) {
        if(spawned) {
            checkBossStatus();
        }
        else {
            spawnBoss();
        }
    }
    
    private void checkBossStatus() {
        if(Application.get().getLogic().getActor(bossID) == null) {
            // boss is dead!
            dead = true;
        }
    }
    
    @Override
    public boolean finished() {
        return dead;
    }
}
