package settii.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class MouseControl extends MouseAdapter {
    private InputControl controller;
    
    public MouseControl(InputControl ic) {
        controller = ic;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        controller.buttonDown(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        controller.buttonUp(e);
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        controller.pointerMove(e);
    }
}
