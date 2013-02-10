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
/**
 *
 * @author Merioksan Mikko
 */
public class HumanView implements IGameView {
    private Camera camera;
    
    private GameActor attachedActor;
    
    ArrayList<Long> playerWeapons;
    ArrayList<Long> selectedWeapons;
    
    private GameScene scene;
    private ArrayDeque<IGameScreen> screens;

    public HumanView() {
        scene = new GameScene();
        screens = new ArrayDeque<IGameScreen>();
        playerWeapons = new ArrayList<Long>();
        selectedWeapons = new ArrayList<Long>();
        
        camera = new Camera();
    }
    
    public boolean init() {
        if(!scene.init()) {
            System.out.println("Error initializing the scene");
            return false;
        }
        
        Application.get().getEventManager().register(ActorSelectedEvent.eventType, new ActorSelectedListener(this));
        Application.get().getEventManager().register(SelectedActorsClearedEvent.eventType, new SelectedActorsClearedListener(this));
        
        return true;
    }

    public GameActor getAttachedActor() {
        return attachedActor;
    }
    public void attachActor(GameActor actor) {
        attachedActor = actor;
    }
    
    public ArrayList<Long> getPlayerWeapons() {
        return playerWeapons;
    }
    public ArrayList<Long> getSelectedWeapons() {
        return selectedWeapons;
    }
    public void removeSelectedWeapon(long id) {
        selectedWeapons.remove(id);
        scene.setGraphic(id, "default");
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
        Renderer.get().begin();
        Renderer.get().getSpriteBatch().getShader().setUniformf("offset", x, y);
        scene.render();
        Renderer.get().end();
        
        
        // render user interface stuff
        Renderer.get().begin();
        // set offset uniform to zero for user interface rendering
        Renderer.get().getSpriteBatch().getShader().setUniformf("offset", 0, 0);
        Iterator<IGameScreen> it = screens.descendingIterator();
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            screen.render();
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
        while(Mouse.next()) {
            int button = Mouse.getEventButton();
            int mX = Mouse.getX();
            int mY = Display.getHeight() - Mouse.getY(); // invert the y-axis to match all our coordinates
            if(button != -1) { // check that a state of a mouse button has changed
                if(Mouse.getEventButtonState()) {
                    onMouseDown(mX, mY, button);
                }
                else {
                    onMouseUp(mX, mY, button);
                }
            }
        }
        
        int mDX = Mouse.getDX();
        int mDY = -Mouse.getDY(); // invert y-axis yet again
        
        if(mDX != 0 || mDY != 0) {
            onPointerMove(mDX, mDY);
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
        
        if(scene.onKeyDown(key)) {
            return true;
        }
        
        if(camera.onKeyDown(key)) {
            return true;
        }
        
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
        
        if(scene.onKeyUp(key)) {
            return true;
        }
        
        if(camera.onKeyUp(key)) {
            return true;
        }
        
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
        mX = (int)Math.abs(mX - camera.getX());
        mY = (int)Math.abs(mY - camera.getY());
        if(scene.onMouseDown(mX, mY, button)) {
            return true;
        }
        
        if(camera.onMouseDown(mX, mY, button)) {
            return true;
        }
        
        for(long a : selectedWeapons) {
            Application.get().getEventManager().queueEvent(new FireWeaponEvent(a));
        }
        
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
        
        // to change mouse coordinates to "world" coordinates, we must take camera location into accord
        mX = (int)Math.abs(mX - camera.getX());
        mY = (int)Math.abs(mY - camera.getY());
        if(scene.onMouseUp(mX, mY, button)) {
            return true;
        }
        
        if(camera.onMouseUp(mX, mY, button)) {
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean onPointerMove(int mDX, int mDY) {
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onPointerMove(mDX, mDY)) {
                return true;
            }
        }
        
        if(scene.onPointerMove(mDX, mDY)) {
            return true;
        }
        
        if(camera.onPointerMove(mDX, mDY)) {
            return true;
        }
        
        return false;
    }
    
    public void actorSelectedListener(ActorSelectedEvent ase) {
        selectedWeapons.add(ase.getActor());
        scene.setGraphic(ase.getActor(), "selected");
    }
    
    public void selectedActorsClearedListener() {
        for(long id : selectedWeapons) {
            scene.setGraphic(id, "default");
        }
        selectedWeapons.clear();
    }
}
