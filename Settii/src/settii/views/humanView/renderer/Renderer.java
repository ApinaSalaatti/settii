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
    
    private SpriteBatch batch;
    
    public Renderer() {
        try {
            batch = new SpriteBatch();
        } catch(Exception e) {
            System.out.println("Renderer creation failed!");
        }
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
        //System.out.println("Actor: " + ro.getActor() + ", x: " + ro.getX() + ", y: " + ro.getY());
        batch.draw(ro.getTexture(), ro.getX(), ro.getY(), ro.getWidth(), ro.getHeight(), 0, 0, ro.getRotation(), ro.getTexture().getU(), ro.getTexture().getV(), ro.getTexture().getU2(), ro.getTexture().getV2());
    }
    
    public void draw(RenderObject ro, float width, float height) {
        batch.draw(ro.getTexture(), ro.getX(), ro.getY(), width, height);
    }
}
