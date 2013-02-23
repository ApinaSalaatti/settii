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
import settii.views.humanView.renderer.BitmapFont;
import settii.views.humanView.renderer.Texture;
/**
 *
 * @author Merioksan Mikko
 */
public class HumanView implements IGameView {
    private Camera camera;
    
    // cursor stuff
    private Texture cursor;
    private float mouseX, mouseY;
    
    private long attachedActor;
    
    private GameScene scene;
    private ArrayDeque<IGameScreen> screens;

    public HumanView() {
        scene = new GameScene();
        screens = new ArrayDeque<IGameScreen>();
        
        camera = new Camera();
        
        screens.addFirst(GameplayScreenFactory.create());
    }
    
    public boolean init() {
        if(!scene.init()) {
            System.out.println("Error initializing the scene");
            return false;
        }
        
        cursor = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/cursor.png");
        mouseX = 0;
        mouseY = Display.getHeight();
        return true;
    }

    /*
    public long getAttachedActor() {
        return attachedActor;
    }
    public void attachActor(long actor) {
        attachedActor = actor;
    }
    */
    
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
        
        // render user interface stuff
        // set offset uniform to zero for user interface rendering
        Renderer.get().setOffset(0, 0);
        Renderer.get().begin();
        Iterator<IGameScreen> it = screens.descendingIterator();
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            screen.render();
        }
        
        // render cursor last
        Renderer.get().draw(cursor, mouseX-cursor.getWidth()/2, mouseY-cursor.getHeight()/2);
        
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
        while(Mouse.next()) {
            int button = Mouse.getEventButton();
            int mX = Mouse.getEventX();
            int mY = Display.getHeight() - Mouse.getEventY(); // invert the y-axis to match all our coordinates
            if(button != -1) { // check that a state of a mouse button has changed
                if(Mouse.getEventButtonState()) {
                    onMouseDown(mX, mY, button);
                }
                else {
                    onMouseUp(mX, mY, button);
                }
            }
        }
        
        int mX = Mouse.getX();
        int mY = Display.getHeight() - Mouse.getY(); // invert the y-axis to match all our coordinates
        mouseX = mX;
        mouseY = mY;
        int mDX = Mouse.getDX();
        int mDY = -Mouse.getDY(); // invert y-axis yet again
        
        if(mDX != 0 || mDY != 0) {
            onPointerMove(mX, mY, mDX, mDY);
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
}
