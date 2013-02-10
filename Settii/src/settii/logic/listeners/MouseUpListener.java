/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.logic.listeners;

import settii.logic.SettiLogic;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.MouseUpEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class MouseUpListener implements IGameEventListener {
    private SettiLogic sl;
    
    public MouseUpListener(SettiLogic sl) {
        this.sl = sl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        MouseUpEvent mue = (MouseUpEvent)event;
        sl.MouseUpListener(mue.getX(), mue.getY(), mue.getButton());
    }
}
