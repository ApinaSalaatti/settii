package settii;

import java.util.Random;
import settii.logic.*;
import settii.eventManager.*;
import settii.views.humanView.HumanView;
import settii.views.humanView.renderer.Renderer;
import settii.resourceManager.ResourceManager;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import settii.processManager.ProcessManager;

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
    public static String WINDOW_TITLE = "Cockblocker";
    public static boolean FULLSCREEN = false;
    public static boolean VSYNC = true;
    
    public static long lifetime;
    private long fpsCounter, elapsed;
    public long fps;
    
    private Random rng;
    
    private ResourceManager resManager;
    private ProcessManager processManager;
    
    private GameLogic logic;
    private HumanView humanView;
    private EventManager eventManager;
    private Options options;
    
    private boolean quitting;
    
    public Application() {
        rng = new Random();
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
    
    public ResourceManager getResourceManager() {
        return resManager;
    }
    
    public ProcessManager getProcessManager() {
        return processManager;
    }
    
    public Random getRNG() {
        return rng;
    }
    
    public Options getOptions() {
        return options;
    }
    
    /**
     * Initialize stuff..
     * 
     * @return was initialization succesful
     */
    public boolean init() {
        quitting = false;
        lifetime = 0;
        
        try {
            resManager = new ResourceManager();
            processManager = new ProcessManager();
            
            options = new Options();
            options.createFromXML(resManager.getDataManager().getData("assets/data/options.xml"));
            
            // initiate the window
            Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
            Display.setTitle(WINDOW_TITLE);
            Display.setResizable(true); //whether our window is resizable
            Display.setVSyncEnabled(VSYNC); //whether hardware VSync is enabled
            Display.setFullscreen(FULLSCREEN); //whether fullscreen is enabled
            
            //Mouse.setGrabbed(true);

            //create and show our display
            Display.create();
            
            resize();
            
            Renderer.create();
        } catch (LWJGLException e) {
            System.out.println("Error initializing display!");
            return false;
        }
        
        eventManager = new EventManager();
        if(!eventManager.init()) {
            System.out.println("Error initializing event manager!");
            return false;
        }
        
        humanView = new HumanView();
        if(!humanView.init()) {
            System.out.println("Error initializing view!");
            return false;
        }
        
        logic = new GameLogic();
        if(!logic.init()) {
            System.out.println("Error initializing logic!");
            return false;
        }
        
        try {
            resManager.preloadResources();
            /*
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/background.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/cursor.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/mainmenuscreen/background.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/mainmenuscreen/quitButton.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/mainmenuscreen/startGameButton.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/background.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/baseHealthy.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/baseDamaged.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/baseCritical.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/mapDisplay.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/researchscreen/background.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/gameplayscreen/commandCenterButton.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/cursor.png");
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            Renderer.get().begin();
            Renderer.get().drawText("LOADING STUFF...", 200, 300);
            Renderer.get().drawText("SOUNDS", 200, 350);
            Renderer.get().end();
            Display.update();
            File file = new File("assets/audio/");
            File[] files = file.listFiles();
            for(File f : files) {
                if(f.isFile()) {
                    System.out.println(f.getPath());
                    Application.get().getResourceManager().getAudioManager().getSound(f.getPath());
                }
            }
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            Renderer.get().begin();
            Renderer.get().drawText("LOADING STUFF...", 200, 300);
            Renderer.get().drawText("DATA", 200, 350);
            Renderer.get().end();
            Display.update();
            Application.get().getResourceManager().getDataManager().getData("assets/data/actors/cannon.xml");
            Application.get().getResourceManager().getDataManager().getData("assets/data/actors/bigCannon.xml");
            Application.get().getResourceManager().getDataManager().getData("assets/data/actors/enemy.xml");
            Application.get().getResourceManager().getDataManager().getData("assets/data/actors/base.xml");
            Application.get().getResourceManager().getDataManager().getData("assets/data/actors/bullet.xml");
            Application.get().getResourceManager().getDataManager().getData("assets/data/actors/base.xml");
            Application.get().getResourceManager().getDataManager().getData("assets/data/research.xml");
            Application.get().getResourceManager().getDataManager().getData("assets/data/shop.xml");
            * 
            */
        }
        catch(Exception e) {
            System.out.println("Failed to load assets!");
        }
        
        return true;
    }
    
    /**
     * Resize the window.
     */
    public void resize() {
        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
    }
    
    /**
     * Run the game!
     */
    public void run() {
        long currentTime = System.currentTimeMillis();
        long lastTime;
        long deltaMs;
        
        options.save();
        
        fpsCounter = 0;
        elapsed = 0;
        //resManager.getAudioManager().play();
        while(!quitting && !Display.isCloseRequested()) {
            lastTime = currentTime;
            currentTime = System.currentTimeMillis();
            deltaMs = currentTime - lastTime;
            
            lifetime += deltaMs;
            elapsed += deltaMs;
            fpsCounter++;
            if(elapsed >= 1000) {
                //System.out.println(fpsCounter);
                fps = fpsCounter;
                fpsCounter = 0;
                elapsed = 0;
            }
            
            // If the window was resized, we need to update our projection
            if (Display.wasResized()) {
                    resize();
            }
            
            logic.update(deltaMs);
            humanView.update(deltaMs);
            eventManager.update();
            
            Display.update();
        }
        
        resManager.destroy();
        Display.destroy();
    }
    
    public void quit() {
        quitting = true;
    }
}
