package settii.logic.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.WeaponFiredEvent;
import settii.logic.SettiLogic;

/**
 *
 * @author Merioksan Mikko
 */
public class WeaponFiredListener implements IGameEventListener {
    private SettiLogic logic;
    
    public WeaponFiredListener(SettiLogic sl) {
        logic = sl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        WeaponFiredEvent wfe = (WeaponFiredEvent)event;
        
        logic.weaponFiredListener(wfe.getActor());
    }
    
}
