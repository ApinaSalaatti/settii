package settii.logic.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.EnemyDestroyedEvent;
import settii.eventManager.events.IGameEvent;
import settii.logic.Player;

/**
 *
 * @author Merioksan Mikko
 */
public class EnemyDestroyedListener implements IGameEventListener {
    private Player player;
    
    public EnemyDestroyedListener(Player p) {
        player = p;
    }
    
    @Override
    public void execute(IGameEvent event) {
        EnemyDestroyedEvent ede = (EnemyDestroyedEvent)event;
        player.enemyDestroyedListener(ede.getActor());
    }
}
