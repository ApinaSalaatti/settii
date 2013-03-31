package settii.views.humanView.animation;

import java.util.HashMap;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
import settii.actorManager.components.WeaponsComponent;
import settii.views.humanView.renderer.Texture;

/**
 *
 * @author Merioksan Mikko
 */
public class Animation {
    public static String ANIMATION_ATTACK = "attack";
    public static String ANIMATION_IDLE = "idle";
    public static String ANIMATION_MOVING = "moving";
    public static String ANIMATION_DEATH = "death";
    private long actorID;
    private Texture tex;
    private HashMap<String, AnimationData> animations;
    
    private String currentAnimation;
    
    public Animation(long a, String file, int spriteSize) {
        actorID = a;
        tex = Application.get().getResourceManager().getTextureManager().getTexture(file);
        
        /*
        frames = new ArrayDeque<Texture>();
        
        frames.addLast(Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/actors/cannon.png"));
        frames.addLast(Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/actors/cannon2.png"));
        frames.addLast(Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/actors/cannon.png"));
        frames.addLast(Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/actors/cannon3.png"));
        frames.addLast(Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/actors/cannon.png"));
        
        currentFrame = 0;
        frameDelay = 100;
        * 
        */
        
        animations = new HashMap<String, AnimationData>();
        animations.put(ANIMATION_IDLE, new AnimationData(4, 100, 0, 0, spriteSize, spriteSize));
        animations.put(ANIMATION_ATTACK, new AnimationData(4, 100, 0, spriteSize, spriteSize, spriteSize));
        animations.put(ANIMATION_MOVING, new AnimationData(4, 100, 0, spriteSize*2, spriteSize, spriteSize));
        animations.put(ANIMATION_DEATH, new AnimationData(4, 100, 0, spriteSize*3, spriteSize, spriteSize));
        
        currentAnimation = ANIMATION_ATTACK;
    }
    
    public void setAnimation(String a) {
        animations.get(currentAnimation).reset();
        currentAnimation = a;
    }
    
    public void update(long deltaMs) {
        GameActor a = Application.get().getLogic().getActor(actorID);
        if(a != null) {
            WeaponsComponent wc = (WeaponsComponent)a.getComponent("WeaponsComponent");
            PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
            boolean charging = false;
            boolean moving = false;
            if(pc != null && pc.getSpeed() > 0) {
                moving = true;
            }
            if(wc != null && wc.charging()) {
                charging = true;
            }
            
            if(moving && !currentAnimation.equals(ANIMATION_MOVING)) {
                setAnimation(ANIMATION_MOVING);
            }
            else if(charging && !currentAnimation.equals(ANIMATION_ATTACK)) {
                setAnimation(ANIMATION_ATTACK);
            }
            else if(!moving && !charging && !currentAnimation.equals(ANIMATION_IDLE)) {
                setAnimation(ANIMATION_IDLE);
            }
        }
        
        animations.get(currentAnimation).update(deltaMs);
    }
    
    public FrameData currentFrame() {
        return animations.get(currentAnimation).getFrame();
    }
    
    public Texture getTexture() {
        return tex;
    }
    
    public AnimationData getAnimationData(String animation) {
        return animations.get(animation);
    }
    
    public float getFrameWidth() {
        return currentFrame().width;
    }
    public float getFrameHeight() {
        return currentFrame().height;
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
