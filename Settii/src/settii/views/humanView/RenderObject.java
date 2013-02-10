package settii.views.humanView;

import settii.utils.Vector2D;
import settii.views.humanView.renderer.Texture;
import java.util.HashMap;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class RenderObject {
    private long actor;
    
    private float x, y;
    private float width, height;
    private HashMap<String, String> files;
    
    private String filename;
    private Texture tex;
    
    private Vector2D heading;
    
    public RenderObject(long a, float x, float y, Vector2D heading, HashMap<String, String> files) {
        actor = a;
        this.x = x;
        this.y = y;
        
        this.files = files;
        this.heading = heading;
        
        try {
            tex = Application.get().getResourceManager().getTextureManager().getTexture(files.get("default"));
        } catch(Exception e) {
            System.out.println("RenderObject creation failed!");
        }
        
        width = tex.getWidth();
        height = tex.getHeight();
    }
    
    public RenderObject(long a, float x, float y, HashMap<String, String> files) {
        this(a, x, y, new Vector2D(0f, 1f), files);
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
    public long getActor() {
        return actor;
    }
    public String getFilename() {
        return filename;
    }
    public void setFile(String file) {
        filename = file;
        try {
            tex = Application.get().getResourceManager().getTextureManager().getTexture(files.get(file));
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
    
    public Texture getTexture() {
        return tex;
    }
    
    public float getRotation() {
        return heading.getAngleRad();
    }
}
