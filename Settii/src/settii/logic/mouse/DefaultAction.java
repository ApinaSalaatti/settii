/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.logic.mouse;

import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.WeaponsComponent;
import settii.eventManager.events.FireWeaponEvent;

/**
 *
 * @author ApinaSalaatti
 */
public class DefaultAction implements IMouseAction {
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(button == 0) {
            long id = -1;
            if(Application.get().getLogic().getActorAtLoc(mX, mY) != null) {
                id = Application.get().getLogic().getActorAtLoc(mX, mY).getID();
            }
            
            if(Application.get().getLogic().getGame().getPlayerWeapons().contains(id) || Application.get().getLogic().getGame().getBuildings().contains(id)) {
                if(Application.get().getLogic().getGame().getSelectedWeapons().contains(id)) {
                    Application.get().getLogic().getGame().selectActor(id, false);
                }
                else {
                    Application.get().getLogic().getGame().selectActor(id, true);
                }
            }
            else {
                for(long a : Application.get().getLogic().getGame().getSelectedWeapons()) {
                    GameActor actor = Application.get().getLogic().getActor(a);
                    WeaponsComponent wc = (WeaponsComponent)actor.getComponent("WeaponsComponent");
                    if(wc != null) {
                        Application.get().getEventManager().queueEvent(new FireWeaponEvent(a));
                    }
                }
            }
        }
        else if(button == 1) {
            Application.get().getLogic().getGame().clearSelectedWeapons();
        }
        
        return false;
    }
    
    @Override
    public boolean onMouseUp(int mX, int mY, int button) {
        return false;
    }
    
    @Override
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY) {
        return false;
    }
    
    @Override
    public void update(long deltaMs) {
        
    }
}
