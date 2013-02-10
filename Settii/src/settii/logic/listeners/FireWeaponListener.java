package settii.logic.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.FireWeaponEvent;
import settii.logic.GameLogic;
/**
 *
 * @author Merioksan Mikko
 */
public class FireWeaponListener implements IGameEventListener {
    private GameLogic gl;
    
    public FireWeaponListener(GameLogic gl) {
        this.gl = gl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        FireWeaponEvent fwe = (FireWeaponEvent)event;
        
        gl.fireWeaponListener(fwe);
    }
}
