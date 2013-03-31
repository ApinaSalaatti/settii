package settii.resourceManager;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import settii.views.humanView.renderer.Renderer;

/**
 *
 * @author Merioksan Mikko
 */
public class ResourceManager {
    private TextureManager texManager;
    private AudioManager audioManager;
    private DataManager dataManager;
    
    public ResourceManager() {
        texManager = new TextureManager();
        audioManager = new AudioManager();
        dataManager = new DataManager();
    }
    
    public TextureManager getTextureManager() {
        return texManager;
    }
    
    public AudioManager getAudioManager() {
        return audioManager;
    }
    
    public DataManager getDataManager() {
        return dataManager;
    }
    
    public void destroy() {
        audioManager.killALData();
    }
    
    public void preloadResources() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        Renderer.get().begin();
        Renderer.get().drawText("LOADING STUFF...", 200, 300);
        Renderer.get().drawText("GRAPHICS", 200, 350);
        Renderer.get().end();
        Display.update();
        texManager.preload("assets/graphics/");
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        Renderer.get().begin();
        Renderer.get().drawText("LOADING STUFF...", 200, 300);
        Renderer.get().drawText("AUDIO", 200, 350);
        Renderer.get().end();
        Display.update();
        audioManager.preload("assets/audio/");
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        Renderer.get().begin();
        Renderer.get().drawText("LOADING STUFF...", 200, 300);
        Renderer.get().drawText("DATA", 200, 350);
        Renderer.get().end();
        Display.update();
        dataManager.preload("assets/data/");
    }
}
