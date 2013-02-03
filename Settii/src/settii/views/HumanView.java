package settii.views;

import java.awt.Canvas;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import settii.views.ui.*;
import settii.Application;
import settii.actorManager.GameActor;

import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
/**
 *
 * @author Merioksan Mikko
 */
public class HumanView implements IGameView {
    private double cameraX, cameraY;
    
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
        
        cameraX = cameraY = 0;
    }
    
    public boolean init() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        // Enable blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Set clear to transparent black
        GL11.glClearColor(0f, 0f, 0f, 0f);
        
        if(!scene.init()) {
            System.out.println("Error initializing the scene");
            return false;
        }
        
        return true;
    }
    
    public double getCameraX() {
        return cameraX;
    }
    public double getCameraY() {
        return cameraY;
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
    public void addSelectedWeapon(long id) {
        selectedWeapons.add(id);
    }
    public void clearSelectedWeapons() {
        selectedWeapons.clear();
    }
    
    @Override
    public void update(long deltaMs) {
        getInput();
        
        // clear screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        scene.render();
        
        Iterator<IGameScreen> it = screens.descendingIterator();
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            screen.render();
        }
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
    
    public void getInput() {
        // test stuff
        while (Keyboard.next()) {
	    if (Keyboard.getEventKeyState()) {
	        if (Keyboard.getEventKey() == Keyboard.KEY_A) {
		    System.out.println("A Key Pressed");
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_S) {
		    System.out.println("S Key Pressed");
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_D) {
		    System.out.println("D Key Pressed");
		}
	    } else {
	        if (Keyboard.getEventKey() == Keyboard.KEY_A) {
		    System.out.println("A Key Released");
	        }
	    	if (Keyboard.getEventKey() == Keyboard.KEY_S) {
		    System.out.println("S Key Released");
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_D) {
		    System.out.println("D Key Released");
		}
	    }
	}
    }
    
    @Override
    public boolean onButtonDown(InputEvent e) {
        Iterator<IGameScreen> it = screens.iterator();
        
        if(e instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent)e;
        }
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onButtonDown(e)) {
                return true;
            }
        }
        
        if(scene.onButtonDown(e)) {
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean onButtonUp(InputEvent e) {
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onButtonUp(e)) {
                return true;
            }
        }
        
        if(scene.onButtonUp(e)) {
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean onPointerMove(MouseEvent e) {
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onPointerMove(e)) {
                return true;
            }
        }
        
        if(scene.onPointerMove(e)) {
            return true;
        }
        
        return false;
    }
}
