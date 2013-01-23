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

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
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
        
        renderableActors.put(1L, new RenderObject(1L, 10, 10, "lol.png"));
        renderableActors.put(2L, new RenderObject(1L, 80, 80, "lol.png"));
        renderableActors.put(3L, new RenderObject(1L, 150, 150, "lol.png"));
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
    
    public void render() {
        // get camera location
        double camX = Application.get().getHumanView().getCameraX();
        double camY = Application.get().getHumanView().getCameraY();
        
        Collection<RenderObject> objects = renderableActors.values();

        GL11.glPushMatrix();
        for(RenderObject o : objects) {
            GL11.glColor3d(0.0, 1.0, 0.0);
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2d(o.getX() - camX, o.getY() - camY);
                GL11.glVertex2d(o.getX() + o.getWidth() - camX, o.getY() - camY);
                GL11.glVertex2d(o.getX() + o.getWidth() - camX, o.getY() + o.getHeight() - camY);
                GL11.glVertex2d(o.getX() - camX, o.getY() + o.getHeight() - camY);
            GL11.glEnd();
        }
        GL11.glPopMatrix();
        
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
