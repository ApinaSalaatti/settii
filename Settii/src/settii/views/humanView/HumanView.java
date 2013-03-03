package settii.views.humanView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import settii.views.ui.*;
import settii.Application;
import settii.actorManager.GameActor;
import settii.eventManager.events.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import settii.views.IGameView;
import settii.views.humanView.renderer.Renderer;
import settii.views.humanView.listeners.*;
import settii.views.ui.gameplayScreen.*;
import settii.views.ui.mainMenuScreen.*;
import settii.views.ui.commandCenterScreen.*;
import settii.views.humanView.renderer.BitmapFont;
import settii.views.humanView.renderer.Texture;
/**
 *
 * @author Merioksan Mikko
 */
public class HumanView implements IGameView {
    private Camera camera;
    
    // mouse stuff
    private Texture cursor;
    private float mouseX, mouseY;
    private float mSensitivity;
    
    private GameScene scene;
    private ArrayDeque<IGameScreen> screens;
    
    private Tooltip tooltip; // this shit's here so I can render it last. There's prolly a better way?

    public HumanView() {
        scene = new GameScene();
        screens = new ArrayDeque<IGameScreen>();
        
        camera = new Camera();
        
        screens.addFirst(GameplayScreenFactory.create());
        
        Application.get().getEventManager().register(ChangeSectorEvent.eventType, new ChangeSectorListener(this));
        
        tooltip = null;
    }
    
    public boolean init() {
        if(!scene.init()) {
            System.out.println("Error initializing the scene");
            return false;
        }
        
        cursor = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/cursor.png");
        mouseX = Display.getWidth() / 2;
        mouseY = Display.getHeight() / 2;
        
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        //Texture load = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/loading.png");
        Renderer.get().begin();
        //Renderer.get().draw(load, -112, -192);
        Renderer.get().drawText("LOADING STUFF...", 200, 300);
        Renderer.get().end();
        Display.update();
        // load the (biggest and most used) assets
        // TODO: figure out how to do this more efficiently...
        try {
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/cursor.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/background.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/baseHealthy.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/baseDamaged.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/baseCritical.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/mapDisplay.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/researchscreen/background.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/gameplayscreen/commandCenterButton.png");
            Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/cursor.png");
        }
        catch(Exception e) {
            System.out.println("Failed to load assets!");
        }
        
        return true;
    }

    public void setCursor(Texture tex) {
        cursor = tex;
    }
    public void setCursor(String res) {
        cursor = Application.get().getResourceManager().getTextureManager().getTexture(res);
    }
    
    public void setTooltip(Tooltip t) {
        tooltip = t;
    }
    
    @Override
    public void update(long deltaMs) {
        getInput();

        camera.update(deltaMs);
        
        // clear screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        // set the viewing location, inverted so stuff is drawn correctly in the opposite direction
        float x = -camera.getX();
        float y = -camera.getY();
        
        // render the actors
        Renderer.get().setOffset(x, y);
        Renderer.get().begin();
        scene.render();
        Renderer.get().end();
        
        // render and update user interface stuff
        // TODO should we update here?? Propably, so we don't have to iterate through the screens many times...
        // set offset uniform to zero for user interface rendering
        Renderer.get().setOffset(0, 0);
        Renderer.get().begin();
        Iterator<IGameScreen> it = screens.descendingIterator();
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            screen.update(deltaMs);
            screen.render();
        }
        
        // render cursor and tooltip last
        Renderer.get().draw(cursor, mouseX-cursor.getWidth()/2, mouseY-cursor.getHeight()/2);
        if(tooltip != null) {
            tooltip.render();
        }
        
        Renderer.get().end();
    }
    
    public void addScreen(IGameScreen screen) {
        screens.addFirst(screen);
    }
    public IGameScreen popScreen() {
        return screens.pollFirst();
    }
    public void clearScreens() {
        screens.clear();
    }
    
    // keyboard input
    public boolean getInput() {
        while(Keyboard.next()) {
	    if(Keyboard.getEventKeyState()) {
                onKeyDown(Keyboard.getEventKey());
	    } else {
	        onKeyUp(Keyboard.getEventKey());
	    }
	}
        
        // mouse input
        float mDX = Mouse.getDX() * 2.5f; // mouse sensitivity 2.5, testing stuff...
        float mDY = -Mouse.getDY() * 2.5f; // invert y-axis to match all our coordinates
        
        mouseX += mDX;
        mouseY += mDY;
        if(mouseX < 0) {
            mouseX = 0;
        }
        else if(mouseX > Display.getWidth()) {
            mouseX = Display.getWidth();
        }
        if(mouseY < 0) {
            mouseY = 0;
        }
        else if(mouseY > Display.getHeight()) {
            mouseY = Display.getHeight();
        }
        
        while(Mouse.next()) {
            int button = Mouse.getEventButton();
            int mX = Mouse.getEventX();
            int mY = Display.getHeight() - Mouse.getEventY(); // invert the y-axis to match all our coordinates
            if(button != -1) { // check that a state of a mouse button has changed
                if(Mouse.getEventButtonState()) {
                    onMouseDown((int)mouseX, (int)mouseY, button);
                }
                else {
                    onMouseUp((int)mouseX, (int)mouseY, button);
                }
            }
        }
        
        /*
        int mX = Mouse.getX();
        int mY = Display.getHeight() - Mouse.getY(); // invert the y-axis to match all our coordinates
        //mouseX = mX;
        //mouseY = mY;
        int mDX = Mouse.getDX();
        int mDY = -Mouse.getDY(); // invert y-axis yet again
        
        mouseX += mDX * 2.5f;
        mouseY += mDY * 2.5f;
        * 
        */
        
        if(mDX != 0 || mDY != 0) {
            onPointerMove((int)mouseX, (int)mouseY, (int)mDX, (int)mDY);
        }
        
        return false;
    }
    
    @Override
    public boolean onKeyDown(int key) {
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onKeyDown(key)) {
                return true;
            }
        }
        
        if(camera.onKeyDown(key)) {
            return true;
        }
        
        if(key == Keyboard.KEY_C || key == Keyboard.KEY_ESCAPE) {
            addScreen(CommandCenterScreenFactory.create());
        }
        
        Application.get().getEventManager().queueEvent(new KeyDownEvent(key));
        
        return false;
    }
    
    @Override
    public boolean onKeyUp(int key) {
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onKeyUp(key)) {
                return true;
            }
        }
        
        if(camera.onKeyUp(key)) {
            return true;
        }
        
        Application.get().getEventManager().queueEvent(new KeyUpEvent(key));
        
        return false;
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onMouseDown(mX, mY, button)) {
                return true;
            }
        }
        
        // to change mouse coordinates to "world" coordinates, we must take camera location to accord
        mX = (int)(mX + camera.getX());
        mY = (int)(mY + camera.getY());
        
        if(camera.onMouseDown(mX, mY, button)) {
            return true;
        }
        
        Application.get().getEventManager().queueEvent(new MouseDownEvent(mX, mY, button));
        
        return false;
    }
    
    @Override
    public boolean onMouseUp(int mX, int mY, int button) {
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onMouseUp(mX, mY, button)) {
                return true;
            }
        }
        
        if(camera.onMouseUp(mX, mY, button)) {
            return true;
        }
        
        // to change mouse coordinates to "world" coordinates, we must take camera location into accord
        mX = (int)(mX + camera.getX());
        mY = (int)(mY + camera.getY());
        
        Application.get().getEventManager().queueEvent(new MouseUpEvent(mX, mY, button));
        
        return false;
    }
    
    @Override
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY) {
        
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onPointerMove(mX, mY, mDX, mDY)) {
                return true;
            }
        }
        
        if(camera.onPointerMove(mX, mY, mDX, mDY)) {
            return true;
        }
        
        // to change mouse coordinates to "world" coordinates, we must take camera location into accord
        mX = (int)(mX + camera.getX());
        mY = (int)(mY + camera.getY());
        
        Application.get().getEventManager().queueEvent(new PointerMoveEvent(mX, mY, mDX, mDY));
        
        return false;
    }
    
    public void changeSectorListener(long id) {
        float sX = Application.get().getLogic().getGame().getSector(id).getX();
        float sY = Application.get().getLogic().getGame().getSector(id).getY();
        
        camera.setLocation(sX, sY);
    }
}
