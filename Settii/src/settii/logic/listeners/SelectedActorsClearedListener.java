package settii.logic.listeners;

import settii.logic.SettiLogic;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.logic.SettiLogic;
/**
 *
 * @author Merioksan Mikko
 */
public class SelectedActorsClearedListener implements IGameEventListener {
    private SettiLogic sl;
    
    public SelectedActorsClearedListener(SettiLogic sl) {
        this.sl = sl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        sl.selectedActorsClearedListener();
    }
}
