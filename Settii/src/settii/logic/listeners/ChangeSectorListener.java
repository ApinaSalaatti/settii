package settii.logic.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.ChangeSectorEvent;
import settii.logic.SettiLogic;
/**
 *
 * @author Merioksan Mikko
 */
public class ChangeSectorListener implements IGameEventListener {
    private SettiLogic logic;
    
    public ChangeSectorListener(SettiLogic sl) {
        logic = sl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        ChangeSectorEvent cse = (ChangeSectorEvent)event;
        
        logic.changeSectorListener(cse.getID());
    }
}
