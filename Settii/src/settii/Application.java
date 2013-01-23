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
import org.lwjgl.opengl.GL11;

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
    public static String WINDOW_TITLE = "Rockin' and rollin'!";
    public static long lifetime;
    
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
            // initiate the window
            Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
            Display.setTitle(WINDOW_TITLE);
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
        
        humanView = new HumanView();
        if(!humanView.init()) {
            System.out.println("Error initializing view!");
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
            
            Display.update();
            logic.update(deltaMs);
            humanView.update(deltaMs);
            eventManager.update();
        }
        
        Display.destroy();
    }
}
