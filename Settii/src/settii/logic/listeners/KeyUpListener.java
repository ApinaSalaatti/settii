/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.logic.listeners;
import settii.logic.SettiLogic;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.KeyUpEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class KeyUpListener implements IGameEventListener {
    private SettiLogic sl;
    
    public KeyUpListener(SettiLogic sl) {
        this.sl = sl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        KeyUpEvent kue = (KeyUpEvent)event;
        sl.KeyUpListener(kue.getKey());
    }
}