package settii.utils.actions;

import settii.Application;
import settii.logic.EnemyManager;
import settii.logic.sectors.GameSector;

/**
 *
 * @author Merioksan Mikko
 */
public class RandomEnemySpawnAction extends Action {
    private long length, betweenSpawns, sinceLastSpawn, elapsed;
    private EnemyManager manager;
    
    public RandomEnemySpawnAction(long l, long between, EnemyManager em) {
        length = l;
        betweenSpawns = between;
        manager = em;
        sinceLastSpawn = 0;
        elapsed = 0;
    }
    
    @Override
    public void update(long deltaMs) {
        sinceLastSpawn += deltaMs;
        elapsed += deltaMs;
        
        if(sinceLastSpawn >= betweenSpawns) {
            int r = Application.get().getRNG().nextInt(4) + 1;
            GameSector sector = Application.get().getLogic().getGame().getSector((long)r);
            if(sector.free()) {
                r = Application.get().getRNG().nextInt(manager.getEnemyPool().size());
                sector.spawnEnemy(manager.getEnemyPool().get(r));
                sinceLastSpawn = 0;
            }
        }
    }
    
    @Override
    public boolean finished() {
        return elapsed >= length;
    }
}
