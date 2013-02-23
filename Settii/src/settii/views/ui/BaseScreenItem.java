package settii.views.ui;

import settii.views.humanView.renderer.Texture;
import settii.views.humanView.renderer.Renderer;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class BaseScreenItem implements IScreenItem {
    protected float x, y;
    protected float xOffset, yOffset;
    protected float width, height;
    
    private Texture sprite;
    
    protected boolean beingDragged;
    protected boolean visible;
    protected boolean clicked, selected;
    
    public BaseScreenItem() {
        beingDragged = false;
        visible = true;
        clicked = false;
        selected = false;
    }
    public BaseScreenItem(float x, float y, float w, float h, String tex) {
        if(tex != null) {
            sprite = Application.get().getResourceManager().getTextureManager().getTexture(tex);
            xOffset = sprite.getWidth() - w;
            yOffset = sprite.getHeight() - h;
        }
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
    public float getX() {
        return x;
    }
    @Override
    public float getY() {
        return y;
    }
    @Override
    public float getWidth() {
        return width;
    }
    @Override
    public float getHeight() {
        return height;
    }
    @Override
    public Texture getSprite() {
        return sprite;
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
        if(sprite != null) {
            Renderer.get().draw(sprite, x, y);
        }
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
