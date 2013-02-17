package settii.views.humanView.renderer;

import org.lwjgl.opengl.GL11;
import settii.views.humanView.RenderObject;
import settii.utils.MathUtil;
/**
 *
 * @author Merioksan Mikko
 */
public class Renderer {
    private static Renderer instance = new Renderer();
    
    private float offsetX, offsetY;
    
    private SpriteBatch batch;
    
    public Renderer() {
        try {
            batch = new SpriteBatch();
        } catch(Exception e) {
            System.out.println("Renderer creation failed!");
            e.printStackTrace();
        }
    }
    
    public void setOffset(float x, float y) {
        offsetX = x;
        offsetY = y;
    }
    
    public static Renderer get() {
        return instance;
    }
    
    public static void create() {
        instance = new Renderer();
        instance.init();
    }
    
    public void init() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        // Enable blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Set clear to opaque white
        GL11.glClearColor(1f, 1f, 1f, 1f);
    }
    
    public void begin() {
        batch.begin();
    }
    
    public void end() {
        batch.end();
    }
    
    public SpriteBatch getSpriteBatch() {
        return batch;
    }
 
    public void draw(RenderObject ro) {
        // offset objects according to the set camera offset
        float x = ro.getX() + offsetX;
        float y = ro.getY() + offsetY;
        
        // draw sprites so that the location of the actor is in the center of the sprite
        x = x - ro.getTexture().getWidth() / 2;
        y = y - ro.getTexture().getHeight() / 2;
        
        //System.out.println("Actor: " + ro.getActor() + ", x: " + ro.getX() + ", y: " + ro.getY());
        batch.draw(ro.getTexture(), x, y, ro.getWidth(), ro.getHeight(), ro.getTexture().getWidth() / 2, ro.getTexture().getHeight() / 2, MathUtil.toRenderAngle(ro.getRotation()), ro.getTexture().getU(), ro.getTexture().getV(), ro.getTexture().getU2(), ro.getTexture().getV2());
    }
    
    public void draw(RenderObject ro, float width, float height) {
        batch.draw(ro.getTexture(), ro.getX(), ro.getY(), width, height);
    }
}
