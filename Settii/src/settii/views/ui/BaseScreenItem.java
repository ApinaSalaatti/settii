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
    
    protected Texture sprite;
    
    protected boolean beingDragged;
    protected boolean visible;
    protected boolean clicked, selected;
    
    // tooltip stuff! Just testing...
    protected boolean pointerOver, renderTooltip;
    protected long pointerHovered, tooltipDelay;
    protected Tooltip tooltip;
    
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
        
        pointerOver = false;
        renderTooltip = false;
        pointerHovered = 0;
        tooltipDelay = 1000;
        tooltip = null;
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
    public void setVisible(boolean v) {
        visible = v;
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
    public void update(long deltaMs) {
        if(pointerOver) {
            pointerHovered += deltaMs;
        }
        if(pointerHovered >= tooltipDelay) {
            renderTooltip = true;
            Application.get().getHumanView().setTooltip(tooltip);
        }
        else {
            if(renderTooltip) {
                renderTooltip = false;
                Application.get().getHumanView().setTooltip(null);
            }
        }
    }
    
    @Override
    public void setTooltip(String t) {
        tooltip = new Tooltip();
        tooltip.set(t);
    }
    public void setTooltipDelay(long ms) {
        tooltipDelay = ms;
    }
    
    @Override
    public void render() {
        if(sprite != null && visible) {
            float offsetX = (sprite.getWidth() - width) / 2;
            float offsetY = (sprite.getHeight() - height) / 2;
            
            float drawX = x - offsetX;
            float drawY = y - offsetY;
            
            Renderer.get().draw(sprite, drawX, drawY);
        }
        
        if(renderTooltip && tooltip != null) {
            //tooltip.render();
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
        if(mX > x && mX < x + width && mY > y && mY < y + height) {
            pointerOver = true;
            if(tooltip != null) {
                tooltip.setLocation(mX+20, mY+20);
            }
        }
        else {
            pointerOver = false;
            pointerHovered = 0;
            renderTooltip = false;
            Application.get().getHumanView().setTooltip(null);
        }
        return false;
    }
}
