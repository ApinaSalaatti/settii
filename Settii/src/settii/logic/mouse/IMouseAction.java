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
    public void execute(int mX, int mY, int button);
    public void update(long deltaMs);
    public void render();
}
