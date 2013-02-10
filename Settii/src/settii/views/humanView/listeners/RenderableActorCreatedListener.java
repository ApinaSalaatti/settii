package settii.views.humanView.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.RenderableActorCreatedEvent;
import settii.views.humanView.GameScene;
/**
 *
 * @author Merioksan Mikko
 */
public class RenderableActorCreatedListener implements IGameEventListener {
    private GameScene gs;
    
    public RenderableActorCreatedListener(GameScene gs) {
        this.gs = gs;
    }
    
    @Override
    public void execute(IGameEvent event) {
        RenderableActorCreatedEvent race = (RenderableActorCreatedEvent)event;
        gs.RenderableActorCreatedListener(race);
    }
}
