package settii.views.humanView.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.GameStateChangeEvent;
import settii.views.humanView.HumanView;
/**
 *
 * @author Merioksan Mikko
 */
public class GameStateChangeListener implements IGameEventListener {
    private HumanView view;
    
    public GameStateChangeListener(HumanView hv) {
        view = hv;
    }
    
    @Override
    public void execute(IGameEvent event) {
        GameStateChangeEvent gsce = (GameStateChangeEvent)event;
        view.gameStateChangeListener(gsce.getState());
    }
}
