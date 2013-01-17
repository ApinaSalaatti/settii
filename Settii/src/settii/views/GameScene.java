package settii.views;

import java.awt.Canvas;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
        renderableActors = new HashMap<Long, RenderObject>();
    }
    
    public boolean init() {
        
        renderableActors.put(1L, new RenderObject(1L, 50, 50, "lol.png"));
        initialized = true;
        
        return true;
    }
    
    public boolean initialized() {
        return initialized;
    }
    
    public void setGraphic(long actor, String resource) {
        RenderObject ro = renderableActors.get(actor);
        
        ro.setFilename(resource);
    }
    
    public void render(Graphics2D g) {
        Collection<RenderObject> objects = renderableActors.values();
        
        for(RenderObject o : objects) {
            g.fillRect((int)o.getX(), (int)o.getY(), (int)o.getWidth(), (int)o.getHeight());
        }
    }
    
    public boolean onButtonDown(InputEvent e) {
        return false;
    }
    public boolean onButtonUp(InputEvent e) {
        return false;
    }
    public boolean onPointerMove(MouseEvent e) {
        return false;
    }
}
