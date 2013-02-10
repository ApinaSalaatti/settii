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
    public boolean onMouseDown(int mX, int mY, int button);
    public boolean onMouseUp(int mX, int mY, int button);
    public boolean onKeyDown(int key);
    public boolean onKeyUp(int key);
    public boolean onPointerMove(int mDX, int mDY);
    public void addScreenItem(IScreenItem item);
}
