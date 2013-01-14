package settii.views.ui;

import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
/**
 *
 * @author Merioksan Mikko
 */
public interface IScreenItem {
    public void render(Graphics2D g);
    public boolean isVisible();
    public boolean isSelected();
    public boolean isClicked();
    public boolean beingDragged();
    public boolean onButtonDown(InputEvent e);
    public boolean onButtonUp(InputEvent e);
    public boolean onPointerMove(MouseEvent e);
    public double getX();
    public double getY();
    public double getWidth();
    public double getHeight();
}
