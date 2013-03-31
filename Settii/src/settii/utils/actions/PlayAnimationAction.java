package settii.utils.actions;

import settii.views.humanView.animation.AnimationData;
import settii.views.humanView.animation.FrameData;
import settii.views.humanView.renderer.Texture;

/**
 *
 * @author Merioksan Mikko
 */
public class PlayAnimationAction extends Action {
    private Texture tex;
    private AnimationData data;
    private long length, elapsed;
    private float x, y;

    public PlayAnimationAction(Texture tex, AnimationData data, long length, float x, float y) {
        this.tex = tex;
        this.data = data;
        this.length = length;
        this.elapsed = 0;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void update(long deltaMs) {
        elapsed += deltaMs;
        data.update(deltaMs);
    }
    
    @Override
    public boolean finished() {
        return elapsed >= length;
    }
    
    public FrameData currentFrame() {
        return data.getFrame();
    }
    
    public Texture getTexture() {
        return tex;
    }
    
    public AnimationData getAnimationData(String animation) {
        return data;
    }
    
    public float getFrameWidth() {
        return currentFrame().width;
    }
    public float getFrameHeight() {
        return currentFrame().height;
    }
    
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    
    public float getU() {
        return currentFrame().x / tex.getWidth();
    }
    public float getV() {
        return currentFrame().y / tex.getHeight();
    }
    public float getU2() {
        return (currentFrame().x + currentFrame().width) / tex.getWidth();
    }
    public float getV2() {
        return (currentFrame().y + currentFrame().height) / tex.getHeight();
    }
}
