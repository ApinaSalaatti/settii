package settii.logic.listeners;

import settii.logic.SettiLogic;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.PointerMoveEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class RepairBaseListener implements IGameEventListener {
    private SettiLogic logic;
    
    public RepairBaseListener(SettiLogic sl) {
        logic = sl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        logic.repairBaseListener();
    }
}
