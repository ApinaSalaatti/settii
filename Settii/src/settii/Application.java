package settii;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import settii.logic.*;
import settii.eventManager.*;
import settii.input.*;
import settii.views.HumanView;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Main application class, sits between the player and the game logic and stuff.
 * 
 * @author Merioksan Mikko
 */
public class Application {
    /**
     * Global instance for easy access!
     */
    private static Application instance = new Application();
    
    public static int WINDOW_WIDTH = 800;
    public static int WINDOW_HEIGHT = 640;
    public long lifetime;
    
    private JFrame frame;
    private Canvas canvas;
    
    private GameLogic logic;
    private HumanView humanView;
    private EventManager eventManager;
    private InputControl controller;
    
    private boolean quitting;
    
    public Application() {
        
    }
    
    public static Application get() {
        return instance;
    }
    
    public Canvas getCanvas() {
        return canvas;
    }
    
    public GameLogic getLogic() {
        return logic;
    }
    
    public HumanView getHumanView() {
        return humanView;
    }
    public void setHumanView(HumanView hv) {
        humanView = hv;
    }
    
    public EventManager getEventManager() {
        return eventManager;
    }
    
    public InputControl getInputControl() {
        return controller;
    }
    
    /**
     * Initialize stuff. Do it here so the constructor can't fail.
     * 
     * @return was initialization succesful
     */
    public boolean init() {
        quitting = false;
        lifetime = 0;
        
        try {
            Display.setDisplayMode(new DisplayMode(800,600));
            Display.setTitle("Rockin' and rollin'!");
            Display.create();
        } catch (LWJGLException e) {
            System.out.println("Error initializing display!");
            return false;
        }
        
        logic = new GameLogic();
        if(!logic.init()) {
            System.out.println("Error initializing logic!");
            return false;
        }
        
        eventManager = new EventManager();
        if(!eventManager.init()) {
            System.out.println("Error initializing event manager!");
            return false;
        }
        
        return true;
    }
    
    /**
     * Run the game!
     */
    public void run() {
        long currentTime = System.currentTimeMillis();
        long lastTime;
        long deltaMs;
        
        while(!quitting && !Display.isCloseRequested()) {
            lastTime = currentTime;
            currentTime = System.currentTimeMillis();
            deltaMs = currentTime - lastTime;
            
            lifetime += deltaMs;
            
            logic.update(deltaMs);
            
            Display.update();
        }
    }
}
