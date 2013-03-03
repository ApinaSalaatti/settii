package settii.input;

import org.lwjgl.opengl.Display;
import settii.Application;
import settii.views.humanView.renderer.Texture;

/**
 *
 * @author Merioksan Mikko
 */
public class MouseInput {
    private MouseInput instance = new MouseInput("assets/graphics/ui/cursor.png");
    
    private Texture cursor;
    private float mouseX, mouseY;
    private float mSensitivity;
    
    public MouseInput(String c) {
        cursor = Application.get().getResourceManager().getTextureManager().getTexture(c);
        mouseX = Display.getWidth() / 2;
        mouseY = Display.getHeight() / 2;
    }
    
    public MouseInput get() {
        return instance;
    }
}
