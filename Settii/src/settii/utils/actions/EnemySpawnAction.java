package settii.utils.actions;

import settii.Application;
import settii.logic.sectors.GameSector;

/**
 *
 * @author Merioksan Mikko
 */
public class EnemySpawnAction extends Action {
    public boolean spawned;
    public long sector;
    public String resource;
    
    public EnemySpawnAction(long s, String r) {
        sector = s;
        resource = r;
        spawned = false;
    }
    
    @Override
    public void update(long deltaMs) {
        if(!spawned) {
            GameSector s = Application.get().getLogic().getGame().getSector(sector);
            if(s.free()) {
                s.spawnEnemy(resource);
                spawned = true;
            }
        }
    }
    
    @Override
    public boolean finished() {
        return spawned;
    }
}
