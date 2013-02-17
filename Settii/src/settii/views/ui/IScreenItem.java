package settii.views.ui;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
/**
 *
 * @author Merioksan Mikko
 */
public interface IScreenItem {
    public void render();
    public boolean isVisible();
    public boolean isSelected();
    public boolean isClicked();
    public boolean beingDragged();
    public boolean onMouseDown(int mX, int mY, int button);
    public boolean onMouseUp(int mX, int mY, int button);
    public boolean onKeyDown(int key);
    public boolean onKeyUp(int key);
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY);
    public double getX();
    public double getY();
    public double getWidth();
    public double getHeight();
}
