/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.logic.mouse;

/**
 *
 * @author ApinaSalaatti
 */
public interface IMouseAction {
    // this is prolly unnecessary??
    public void update(long deltaMs);
    
    public boolean onMouseDown(int mX, int mY, int button);
    public boolean onMouseUp(int mX, int mY, int button);
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY);
}
