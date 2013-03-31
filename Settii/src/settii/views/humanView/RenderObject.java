package settii.views.humanView;

import settii.views.humanView.renderer.Texture;
import java.util.HashMap;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
import settii.utils.MathUtil;
import settii.views.humanView.animation.Animation;
/**
 *
 * @author Merioksan Mikko
 */
public class RenderObject implements Comparable {
    private long actor;
    
    private int z;
    
    private float x, y;
    private float width, height;
    private HashMap<String, String> files;
    
    private String filename;
    private Texture tex;
    
    private float angleRad;
    
    private Animation animation;
    
    public RenderObject(long a, float x, float y, float angle, String file, int spriteSize, int z) {
        actor = a;
        this.x = x;
        this.y = y;
        
        this.angleRad = angle;
        
        width = spriteSize;
        height = spriteSize;
        
        animation = new Animation(a, file, spriteSize);
        
        this.z = z;
    }
    
    public RenderObject(long a, float x, float y, String file, int spriteSize, int z) {
        this(a, x, y, MathUtil.ANGLE_STRAIGHT_UP, file, spriteSize, z);
    }
    
    public RenderObject(long a, float x, float y, float w, float h, float angle, String file) {
        z = -1;
        actor = a;
        this.x = x;
        this.y = y;
        
        this.files = new HashMap<String, String>();
        files.put("default", file);
        this.angleRad = angle;
        
        try {
            tex = Application.get().getResourceManager().getTextureManager().getTexture(files.get("default"));
        } catch(Exception e) {
            System.out.println("RenderObject creation failed!");
        }
        
        width = w;
        height = h;
        
        animation = null;
    }
    
    public RenderObject(long a, float x, float y, HashMap<String, String> files) {
        float angle = MathUtil.ANGLE_STRAIGHT_UP;
        
        actor = a;
        this.x = x;
        this.y = y;
        
        this.files = files;
        this.angleRad = angle;
        
        try {
            tex = Application.get().getResourceManager().getTextureManager().getTexture(files.get("default"));
        } catch(Exception e) {
            System.out.println("RenderObject creation failed!");
        }
        
        width = tex.getWidth();
        height = tex.getHeight();
        
        animation = null;
    }
    
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    
    public float getTexWidth() {
        if(animation != null) {
            return animation.getFrameWidth();
        }
        
        return tex.getWidth();
    }
    public float getTexHeight() {
        if(animation != null) {
            return animation.getFrameHeight();
        }
        
        return tex.getHeight();
    }
    
    public long getActor() {
        return actor;
    }
    public String getFilename() {
        return filename;
    }
    public void setFile(String file) {
        filename = file;
        try {
            if(files.containsKey(file)) {
                tex = Application.get().getResourceManager().getTextureManager().getTexture(files.get(file));
            }
            else {
                tex = Application.get().getResourceManager().getTextureManager().getTexture(files.get("default"));
            }
            width = tex.getWidth();
            height = tex.getHeight();
        } catch(Exception e) {
            System.out.println("RenderObject file update failed!");
        }
    }
    
    public void setFile() {
        setFile("default");
    }
    
    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setZ(int z) {
        this.z = z;
    }
    public int getZ() {
        return z;
    }
    
    public Texture getTexture() {
        if(animation != null) {
            return animation.getTexture();
        }
        return tex;
    }
    
    public Animation getAnimation() {
        return animation;
    }
    
    public float getU() {
        if(animation != null) {
            return animation.getU();
        }
        
        return tex.getU();
    }
    
    public float getV() {
        if(animation != null) {
            return animation.getV();
        }
        
        return tex.getV();
    }
    
    public float getU2() {
        if(animation != null) {
            return animation.getU2();
        }
        
        return tex.getU2();
    }
    
    public float getV2() {
        if(animation != null) {
            return animation.getV2();
        }
        
        return tex.getV2();
    }
    
    public float getRotation() {
        return angleRad;
    }
    public void setRotation(float rot) {
        angleRad = rot;
    }
    
    public void update(long deltaMs) {
        GameActor a = Application.get().getLogic().getActor(actor);
        if(a != null) {
            PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
            x = pc.getX();
            y = pc.getY();
            z = pc.getZ();
            setRotation(pc.getAngleRad());
        }
        if(animation != null) {
            animation.update(deltaMs);
        }
    }
    
    @Override
    public int compareTo(Object o) {
        RenderObject ro = (RenderObject)o;
        
        return this.z - ro.getZ();
    }
}
