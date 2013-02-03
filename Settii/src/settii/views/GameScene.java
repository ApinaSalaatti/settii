package settii.views;

import java.awt.Canvas;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import settii.Application;
import java.util.HashMap;
import java.util.Collection;

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
        renderableActors.put(2L, new RenderObject(2L, 80, 80, "lol.png"));
        renderableActors.put(3L, new RenderObject(3L, 150, 150, "lol.png"));
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
        
        try {
            Texture tex = new Texture("cannon.png");
            debugTexture(tex, 10, 10, 300, 300);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        /*
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
        *
        */
        
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
    
    public static void debugTexture(Texture tex, float x, float y, float width, float height) {
	//usually glOrtho would not be included in our game loop
	//however, since it's deprecated, let's keep it inside of this debug function which we will remove later
	GL11.glMatrixMode(GL11.GL_PROJECTION);
	GL11.glLoadIdentity();
	GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
	GL11.glMatrixMode(GL11.GL_MODELVIEW);
	GL11.glLoadIdentity();
	
	//bind the texture before rendering it
	tex.bind();

	//setup our texture coordinates
	//(u,v) is another common way of writing (s,t)
	float u = 0f;
	float v = 0f;
	float u2 = 1f;
	float v2 = 1f;

	//immediate mode is deprecated -- we are only using it for quick debugging
	GL11.glColor4f(1f, 1f, 1f, 1f);
	GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(u, v);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(u, v2);
		GL11.glVertex2f(x, y + height);
		GL11.glTexCoord2f(u2, v2);
		GL11.glVertex2f(x + width, y + height);
		GL11.glTexCoord2f(u2, v);
		GL11.glVertex2f(x + width, y);
	GL11.glEnd();
}

}
