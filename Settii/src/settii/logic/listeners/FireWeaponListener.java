/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.logic.listeners;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.*;
import settii.logic.SettiLogic;
/**
 *
 * @author Merioksan Mikko
 */
public class FireWeaponListener implements IGameEventListener {
    private SettiLogic sl;
    
    public FireWeaponListener(SettiLogic sl) {
        this.sl = sl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        FireWeaponEvent fwe = (FireWeaponEvent)event;
        sl.fireWeaponListener(fwe.getActor());
    }
}
