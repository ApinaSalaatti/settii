package settii.utils.actions;

import settii.Application;
import settii.logic.sectors.GameSector;

/**
 *
 * @author Merioksan Mikko
 */
public class MultipleEnemySpawnsAction extends Action {
    private int amount, spawned;
    private long betweenSpawns, sinceLastSpawn;
    private long sector;
    private String resource;
    
    public MultipleEnemySpawnsAction(long s, String res, long between, int a) {
        sector = s;
        resource = res;
        betweenSpawns = between;
        amount = a;
        spawned = 0;
    }
    
    @Override
    public void update(long deltaMs) {
        sinceLastSpawn += deltaMs;
        if(sinceLastSpawn >= betweenSpawns && spawned < amount) {
            GameSector s = Application.get().getLogic().getGame().getSector(sector);
            if(s.free()) {
                s.spawnEnemy(resource);
                sinceLastSpawn = 0;
                spawned++;
            }
        }
    }
    
    @Override
    public boolean finished() {
        return spawned >= amount;
    }
}
