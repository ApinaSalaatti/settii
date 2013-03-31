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
    public void update(long deltaMs);
    public void makeVisible(boolean v);
    public boolean onMouseDown(int mX, int mY, int button);
    public boolean onMouseUp(int mX, int mY, int button);
    public boolean onKeyDown(int key);
    public boolean onKeyUp(int key);
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY);
    public void addScreenItem(IScreenItem item);
}
