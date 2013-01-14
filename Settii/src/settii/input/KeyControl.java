package settii.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class KeyControl extends KeyAdapter {
    private InputControl controller;
    
    public KeyControl(InputControl ic) {
        controller = ic;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        controller.buttonDown(e);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        controller.buttonUp(e);
    }
}
