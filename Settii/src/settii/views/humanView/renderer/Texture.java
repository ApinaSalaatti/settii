package settii.views.humanView.renderer;

import org.lwjgl.opengl.GL11;
/**
 *
 * @author Merioksan Mikko
 */
public class Texture {
    public int target;
    public int id;
    private int width;
    private int height;

    public Texture(int target, int id) {
        this.target = target;
        this.id = id;
    }
    
    public Texture() {
        
    }
    
    public void create(int target, int id) {
        this.target = target;
        this.id = id;
    }

    public void bind() {
        GL11.glBindTexture(target, id);
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    public int getWidth() {
        return width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    public int getHeight() {
        return height;
    }
    
    public float getU() {
        return 0f;
    }
    public float getV() {
        return 0f;
    }
    public float getU2() {
        return 1f;
    }
    public float getV2() {
        return 1f;
    }
}
