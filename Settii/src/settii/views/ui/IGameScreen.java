package settii.views.ui;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Graphics2D;
/**
 *
 * @author Merioksan Mikko
 */
public interface IGameScreen {
    public void render();
    public boolean onButtonDown(InputEvent e);
    public boolean onButtonUp(InputEvent e);
    public boolean onPointerMove(MouseEvent e);
    public void addScreenItem(IScreenItem item);
}
