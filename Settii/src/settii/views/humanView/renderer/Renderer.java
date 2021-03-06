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
    
    private BitmapFont font;
    
    public Renderer() {
        try {
            batch = new SpriteBatch();
            font = new BitmapFont("assets/graphics/ui/font/characters.png");
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
    
    public BitmapFont getFont() {
        return font;
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
        x = x - ro.getTexWidth() / 2;
        y = y - ro.getTexHeight() / 2;
        
        batch.draw(ro.getTexture(), x, y, ro.getWidth(), ro.getHeight(), ro.getTexWidth() / 2, ro.getTexHeight() / 2, MathUtil.toRenderAngle(ro.getRotation()), ro.getU(), ro.getV(), ro.getU2(), ro.getV2());
    }
    
    public void draw(RenderObject ro, float width, float height) {
        batch.draw(ro.getTexture(), ro.getX(), ro.getY(), width, height);
    }
    
    public void draw(Texture tex, float x, float y) {
        batch.draw(tex, x, y);
    }
    
    public void draw(Texture tex, float x, float y, float w, float h) {
        batch.draw(tex, x, y, w, h);
    }
    
    public void draw(Texture tex, float x, float y, float u, float v, float u2, float v2) {
        batch.draw(tex, x, y, tex.getWidth() * u2, tex.getHeight(), tex.getWidth() / 2, tex.getHeight() / 2, MathUtil.toRenderAngle(MathUtil.ANGLE_STRAIGHT_UP), u, v, u2, v2);
    }
    
    public void draw(Texture tex, float x, float y, float w, float h, float originX, float originY, float angle, float u, float v, float u2, float v2) {
        x = x + offsetX;
        y = y + offsetY;
        x = x - w / 2;
        y = y - h / 2;
        batch.draw(tex, x, y, w, h, originX, originY, MathUtil.toRenderAngle(angle), u, v, u2, v2);
    }
    
    public void draw(Texture tex, float[] vertices, int offset) {
        batch.draw(tex, vertices, offset);
    }
    
    public void drawText(String text, float x, float y) {
        drawText(text, x, y, 1.0f);
    }
    public void drawText(String text, float x, float y, float scale) {
        font.renderText(text, x, y, scale);
    }
}
