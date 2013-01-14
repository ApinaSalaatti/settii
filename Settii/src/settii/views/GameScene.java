package settii.views;

import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import settii.Application;
import java.util.HashMap;
import java.util.Collection;
import java.awt.Graphics2D;
/**
 *
 * @author Merioksan Mikko
 */
public class GameScene {
    private HashMap<Long, RenderObject> renderableActors;
    private boolean initialized;
    
    public GameScene() {
        initialized = false;
    }
    
    public boolean init() {
        initialized = true;
        
        return true;
    }
    
    public boolean initialized() {
        return initialized;
    }
    
    public void render(Graphics2D g) {
        Collection<RenderObject> objects = renderableActors.values();
        
        for(RenderObject o : objects) {
            g.fillRect((int)o.getX(), (int)o.getY(), (int)o.getWidth(), (int)o.getHeight());
        }
    }
}
