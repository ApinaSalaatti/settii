package settii.views.humanView.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.ChangeSectorEvent;
import settii.views.humanView.HumanView;
/**
 *
 * @author Merioksan Mikko
 */
public class ChangeSectorListener implements IGameEventListener {
    private HumanView view;
    
    public ChangeSectorListener(HumanView hv) {
        view = hv;
    }
    
    @Override
    public void execute(IGameEvent event) {
        ChangeSectorEvent sce = (ChangeSectorEvent)event;
        
        view.changeSectorListener(sce.getID());
    }
}
