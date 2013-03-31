package settii.views.humanView.animation;

import java.util.ArrayList;

/**
 *
 * @author Merioksan Mikko
 */
public class AnimationData {
    private ArrayList<FrameData> frames;
    
    private int currentFrame;
    private long frameDelay;
    private long onCurrentFrame;
    
    public AnimationData(int frameCount, long frameDelay, float initialX, float initialY, float frameW, float frameH) {
        this.frames = new ArrayList<FrameData>();
        
        for(int i = 0; i < frameCount; i++) {
            frames.add(new FrameData(initialX+i*frameW, initialY, frameW, frameH));
        }
        
        this.currentFrame = 0;
        this.frameDelay = frameDelay;
        this.onCurrentFrame = 0;
    }
    
    public FrameData getFrame() {
        return frames.get(currentFrame);
    }
    
    public void update(long deltaMs) {
        onCurrentFrame += deltaMs;
        
        if(onCurrentFrame >= frameDelay) {
            currentFrame++;
            currentFrame %= frames.size();
            onCurrentFrame = 0;
        }
    }
    
    public void reset() {
        currentFrame = 0;
        onCurrentFrame = 0;
    }
}
