/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.logic.mouse;

import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.ai.AreaTargeting;
import settii.actorManager.ai.HumanBasicWeaponAI;
import settii.actorManager.components.AIComponent;
import settii.actorManager.components.PhysicsComponent;

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
                    return true;
                }
                else {
                    Application.get().getLogic().getGame().selectActor(id, true);
                    return true;
                }
            }
            else {
                for(long a : Application.get().getLogic().getGame().getSelectedWeapons()) {
                    GameActor actor = Application.get().getLogic().getActor(a);
                    
                    PhysicsComponent pc = (PhysicsComponent)actor.getComponent("PhysicsComponent");
                    pc.setTarget(mX, mY);
                    AIComponent aic = (AIComponent)actor.getComponent("AIComponent");
                    if(aic != null) {
                        if(aic.getAI() instanceof HumanBasicWeaponAI) {
                            HumanBasicWeaponAI hbwai = (HumanBasicWeaponAI)aic.getAI();
                            hbwai.setGuardAngle((float)Math.atan2(pc.getY() - mY, pc.getX() - mX));
                            
                            if(id != -1) {
                                hbwai.setTarget(id);
                            }
                            else {
                                hbwai.setTarget(mX, mY);
                            }
                        }
                    }
                    /*
                    WeaponsComponent wc = (WeaponsComponent)actor.getComponent("WeaponsComponent");
                    if(wc != null) {
                        Application.get().getEventManager().queueEvent(new FireWeaponEvent(a));
                        return true;
                    }
                    */
                }
                Application.get().getLogic().getGame().clearSelectedWeapons();
            }
        }
        else if(button == 1) {
            Application.get().getLogic().getGame().clearSelectedWeapons();
            return true;
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
