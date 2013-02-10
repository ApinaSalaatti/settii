/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.logic.listeners;

import settii.logic.SettiLogic;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.MouseDownEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class MouseDownListener implements IGameEventListener {
    private SettiLogic sl;
    
    public MouseDownListener(SettiLogic sl) {
        this.sl = sl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        MouseDownEvent mde = (MouseDownEvent)event;
        sl.MouseDownListener(mde.getX(), mde.getY(), mde.getButton());
    }
}
