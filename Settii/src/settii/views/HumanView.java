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
/**
 *
 * @author Merioksan Mikko
 */
public class HumanView implements IGameView {
    private double cameraX, cameraY;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    
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
    }
    
    public boolean init() {
        if(!scene.init()) {
            return false;
        }
        
        bufferStrategy = Application.get().getCanvas().getBufferStrategy();
        
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
    public void addSelectedWeapon(long id) {
        selectedWeapons.add(id);
    }
    public void clearSelectedWeapons() {
        selectedWeapons.clear();
    }
    
    @Override
    public void update(long deltaMs) {
        Graphics2D g = (Graphics2D)bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, Application.WINDOW_WIDTH, Application.WINDOW_HEIGHT);
        
        scene.render(g);
        
        Iterator<IGameScreen> it = screens.descendingIterator();
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            screen.render(g);
        }
        
        g.dispose();
        bufferStrategy.show();
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
