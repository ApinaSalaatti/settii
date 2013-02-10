package settii.views.humanView.listeners;

import settii.views.humanView.HumanView;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class SelectedActorsClearedListener implements IGameEventListener {
    private HumanView hv;
    
    public SelectedActorsClearedListener(HumanView hv) {
        this.hv = hv;
    }
    
    @Override
    public void execute(IGameEvent event) {
        hv.selectedActorsClearedListener();
    }
}
