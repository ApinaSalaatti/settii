package settii.views.humanView;

import org.lwjgl.input.Keyboard;

/**
 *
 * @author Merioksan Mikko
 */
public class Camera {
    private float cameraX, cameraY;
    private float cameraSpeedX, cameraSpeedY;
    private boolean screenClicked;
    
    public Camera() {
        this(0, 0);
    }
    /**
     * A constructor that sets the initial camera location
     * 
     * @param x camera x coord
     * @param y camera y coord
     */
    public Camera(int x, int y) {
        cameraX = x;
        cameraY = 0;
    }
    
    public void update(long deltaMs) {
        cameraX += cameraSpeedX;
        cameraY += cameraSpeedY;
        
        if(cameraX < 0) {
            cameraX = 0;
        }
        if(cameraY < 0) {
            cameraY = 0;
        }
    }
    
    public float getX() {
        return cameraX;
    }
    public float getY() {
        return cameraY;
    }
    
    public void setLocation(float x, float y) {
        cameraX = x;
        cameraY = y;
    }
    
    public boolean onKeyDown(int key) {
        /*
        if(key == Keyboard.KEY_LEFT) {
            cameraSpeedX = -1f;
            return true;
        }
        if(key == Keyboard.KEY_RIGHT) {
            cameraSpeedX = 1f;
            return true;
        }
        if(key == Keyboard.KEY_UP) {
            cameraSpeedY = -1f;
            return true;
        }
        if(key == Keyboard.KEY_DOWN) {
            cameraSpeedY = 1f;
            return true;
        }
        * 
        */
        return false;
    }
    public boolean onKeyUp(int key) {
        /*
        if(key == Keyboard.KEY_LEFT) {
            cameraSpeedX = 0.0f;
            return true;
        }
        if(key == Keyboard.KEY_RIGHT) {
            cameraSpeedX = 0.0f;
            return true;
        }
        if(key == Keyboard.KEY_UP) {
            cameraSpeedY = 0.0f;
            return true;
        }
        if(key == Keyboard.KEY_DOWN) {
            cameraSpeedY = 0.0f;
            return true;
        }
        * 
        */
        return false;
    }
    
    public boolean onMouseDown(int mX, int mY, int button) {
        if(button == 0) {
            screenClicked = true;
        }
        return false;
    }
    public boolean onMouseUp(int mX, int mY, int button) {
        if(button == 0) {
            screenClicked = false;
        }
        return false;
    }
    
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY) {
        /*
         * Camera movement.
         * TODO: maybe add something like this?
        if(screenClicked) {
            cameraX -= mDX;
            cameraY -= mDY;
            return true;
        }
        */
        return false;
    }
}
