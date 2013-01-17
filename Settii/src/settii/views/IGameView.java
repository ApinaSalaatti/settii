package settii.views;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
/**
 *
 * @author Merioksan Mikko
 */
public interface IGameView {
    public void update(long deltaMs);
    public boolean onButtonDown(InputEvent e);
    public boolean onButtonUp(InputEvent e);
    public boolean onPointerMove(MouseEvent e);
}
