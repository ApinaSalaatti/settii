package settii.views.ui;

import settii.views.humanView.renderer.Texture;
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
    public void setTooltip(String t);
    public void update(long deltaMs);
    public float getX();
    public float getY();
    public float getWidth();
    public float getHeight();
    public Texture getSprite();
}
