package settii.utils.actions;

import settii.logic.EnemyManager;

/**
 *
 * @author Merioksan Mikko
 */
public class NewEnemyAction extends Action {
    private EnemyManager manager;
    private String resource;
    private boolean added;
    
    public NewEnemyAction(String res, EnemyManager em) {
        manager = em;
        resource = res;
        added = false;
    }
    
    @Override
    public void update(long deltaMs) {
        manager.addEnemy(resource);
        added = true;
    }
    
    @Override
    public boolean finished() {
        return added;
    }
}
