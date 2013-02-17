/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.logic.listeners;
import settii.logic.SettiLogic;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.PointerMoveEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class PointerMoveListener implements IGameEventListener {
    private SettiLogic sl;
    
    public PointerMoveListener(SettiLogic sl) {
        this.sl = sl;
    }
    
    @Override
    public void execute(IGameEvent event) {
        PointerMoveEvent pme = (PointerMoveEvent)event;
        sl.PointerMoveListener(pme.getX(), pme.getY(), pme.getDX(), pme.getDY());
    }
}