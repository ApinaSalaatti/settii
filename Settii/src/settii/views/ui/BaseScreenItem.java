package settii.views.ui;

import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class BaseScreenItem implements IScreenItem {
    protected double x, y;
    protected double width, height;
    protected boolean beingDragged;
    protected boolean visible;
    protected boolean clicked, selected;
    
    public BaseScreenItem() {
        beingDragged = false;
        visible = true;
        selected = false;
    }
    public BaseScreenItem(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        beingDragged = false;
        visible = true;
        clicked = false;
        selected = false;
    }
    
    @Override
    public double getX() {
        return x;
    }
    @Override
    public double getY() {
        return y;
    }
    @Override
    public double getWidth() {
        return width;
    }
    @Override
    public double getHeight() {
        return height;
    }
    
    @Override
    public boolean beingDragged() {
        return beingDragged;
    }
    
    @Override
    public boolean isVisible() {
        return visible;
    }
    
    @Override
    public boolean isSelected() {
        return selected;
    }
    
    @Override
    public boolean isClicked() {
        return clicked;
    }
    
    @Override
    public void render() {
    }
    
    @Override
    public boolean onKeyDown(int key) {
        return false;
    }
    @Override
    public boolean onKeyUp(int key) {
        return false;
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        clicked = true;
        return false;
    }
    
    @Override
    public boolean onMouseUp(int mX, int mY, int button) {
        clicked = false;
        return false;
    }
    
    @Override
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY) {
        return false;
    }
}
