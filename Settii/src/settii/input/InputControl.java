package settii.input;

import java.awt.MouseInfo;
import java.awt.event.*;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class InputControl {
    public double mouseX;
    public double mouseY;
    
    private boolean[] keys;
    
    public InputControl() {
        keys = new boolean[1024];
    }
    
    public void buttonDown(InputEvent e) {
        if(e instanceof MouseEvent) {
            MouseEvent me = (MouseEvent)e;
            //Application.get().getLogic().onButtonDown(e);
        }
        
        if(e instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent)e;
            keys[ke.getKeyCode()] = true;
            //Application.get().getLogic().onButtonDown(e);
        }
    }
    
    public void buttonUp(InputEvent e) {
        if(e instanceof MouseEvent) {
            MouseEvent me = (MouseEvent)e;
            //Application.get().getLogic().onButtonUp(e);
        }
        
        if(e instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent)e;
            keys[ke.getKeyCode()] = false;
            //Application.get().getLogic().onButtonUp(e);
        }
    }
    
    public void pointerMove(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        //Application.get().getLogic().onPointerMove(e);
    }
    
}
