package settii.views.ui.gameplayScreen;

import java.util.Iterator;
import settii.views.ui.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import settii.actorManager.GameActor;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class GameplayScreen extends BaseGameScreen {
    public GameplayScreen() {
        
    }
    
    @Override
    public boolean onButtonDown(InputEvent e) {
        if(super.onButtonDown(e)) {
            return true;
        }
        
        if(e instanceof MouseEvent) {
            MouseEvent me = (MouseEvent)e;
            if(me.getButton() == MouseEvent.BUTTON1) {
                handleLeftClick(me);
                return true;
            }
            else if(me.getButton() == MouseEvent.BUTTON3) {
                Application.get().getHumanView().clearSelectedWeapons();
            }
        }
        else if(e instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent)e;
            ArrayList<Long> weapons = Application.get().getHumanView().getPlayerWeapons();
            
        }
        return true;
    }
    
    @Override
    public boolean onButtonUp(InputEvent e) {
        if(e instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent)e;
            
        }
        return true;
    }
    
    @Override
    public boolean onPointerMove(MouseEvent e) {
        
        return true;
    }
    
    public void handleLeftClick(MouseEvent me) {
        GameActor actor = Application.get().getLogic().getActorAtLoc(me.getX(), me.getY());
        if(actor != null && actor.getComponent("SelectableComponent") != null) {
            Application.get().getHumanView().addSelectedWeapon(actor.getID());
        }
        else {
            
        }
    }
}
