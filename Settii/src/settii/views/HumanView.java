package settii.views;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayDeque;
import java.util.Iterator;
import settii.views.ui.*;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class HumanView implements IGameView {
    private double cameraX, cameraY;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    
    private GameScene scene;
    private ArrayDeque<IGameScreen> screens;

    public HumanView() {
        scene = new GameScene();
        screens = new ArrayDeque<IGameScreen>();
    }
    
    public boolean init() {
        if(!scene.init()) {
            return false;
        }
        
        return true;
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
}
