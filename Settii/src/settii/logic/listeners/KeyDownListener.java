/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.logic.listeners;

import settii.logic.SettiLogic;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.KeyDownEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class KeyDownListener implements IGameEventListener {
    private SettiLogic sl;
    
    public KeyDownListener(SettiLogic sl) {
        this.sl = sl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        KeyDownEvent kde = (KeyDownEvent)event;
        sl.KeyDownListener(kde.getKey());
    }
}
