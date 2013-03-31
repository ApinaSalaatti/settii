package settii.views.ui.gameplayScreen;

import settii.views.ui.*;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import settii.Application;
import settii.eventManager.events.GameStateChangeEvent;
import settii.logic.GameLogic;
import settii.logic.WeaponLocation;
import settii.views.humanView.renderer.Texture;
import settii.views.ui.console.ConsoleFactory;
import settii.views.ui.pauseScreen.PauseScreenFactory;
/**
 *
 * @author Merioksan Mikko
 */
public class GameplayScreen extends BaseGameScreen {
    private Texture attackArea;
    private float[] data;
    
    private boolean locsUpdated;
    
    private float targetX, targetY;
    
    private ArrayList<WeaponLocationDisplay> weaponLocDisplays;
    
    public GameplayScreen() {
        attackArea = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/gameplayscreen/attackArea.png");
        data = new float[48];
        targetX = targetY = 0;
        
        weaponLocDisplays = new ArrayList<WeaponLocationDisplay>();
        locsUpdated = false;
    }
    
    @Override
    public void render() {
        /*
        ArrayList<Long> actors = Application.get().getLogic().getGame().getSelectedWeapons();
        for(Long l : actors) {
            GameActor actor = Application.get().getLogic().getActor(l);
            if(actor != null) {
                PhysicsComponent pc = (PhysicsComponent)actor.getComponent("PhysicsComponent");
                WeaponsComponent wc = (WeaponsComponent)actor.getComponent("WeaponsComponent");
                // get the location of the actor in relation to the camera
                float x = pc.getX() - Application.get().getHumanView().getCamera().getX();
                float y = pc.getY() - Application.get().getHumanView().getCamera().getY();
                float range = 0;
                
                if(wc != null) {
                    range = wc.getRange();
                }
                
                Renderer.get().draw(attackArea, x - range, y - range, range * 2, range * 2);
                /*
                PhysicsComponent pc = (PhysicsComponent)actor.getComponent("PhysicsComponent");
                WeaponsComponent wc = (WeaponsComponent)actor.getComponent("WeaponsComponent");
                // get the location of the actor in relation to the camera
                float x = pc.getX() - Application.get().getHumanView().getCamera().getX();
                float y = pc.getY() - Application.get().getHumanView().getCamera().getY();
                
                float rXOffset = (float)Math.sin(wc.getAttackAngle()) * wc.getRange();
                float rYOffset = wc.getRange() - (float)Math.cos(wc.getAttackAngle()) * wc.getRange();
                
                float width = rXOffset * 2;
                float height = wc.getRange();
                float X = x - rXOffset;
                float Y = y - height;
                
                float dx = x - targetX;
                float dy = y - targetY;
        
                float angle = (float)Math.atan2(dy, dx);
                
                Renderer.get().draw(attackArea, X, Y, width, height, width / 2, height, angle, 0, 0, 1.0f, 1.0f);
                */
                /*
                float xTopLeft = x - rXOffset;
                float yTopLeft = y - wc.getRange() + rYOffset;
                
                float xTopRight = x;
                float yTopRight = y - wc.getRange();
                
                float xBottomLeft = x;
                float yBottomLeft = y;
                
                float xBottomRight = x + rXOffset;
                float yBottomRight = y - wc.getRange() + rYOffset;

                float cos = (float)Math.cos(MathUtil.toRenderAngle(pc.getAngleRad()));
                float sin = (float)Math.sin(MathUtil.toRenderAngle(pc.getAngleRad()));
                
                xTopLeft = (xTopLeft - x) * cos - (yTopLeft - y) * sin + x;
                yTopLeft = (yTopLeft - y) * cos + (xTopLeft - x) * sin + y; 
                
                xTopRight = (xTopRight - x) * cos - (yTopRight - y) * sin + x;
                yTopRight = (yTopRight - y) * cos + (xTopRight - x) * sin + y; 
                
                xBottomRight = (xBottomRight - x) * cos - (yBottomRight - y) * sin + x;
                yBottomRight = (yBottomRight - y) * cos + (xBottomRight - x) * sin + y; 
                
                xBottomLeft = (xBottomLeft - x) * cos - (yBottomLeft - y) * sin + x;
                yBottomLeft = (yBottomLeft - y) * cos + (xBottomLeft - x) * sin + y; 
                */
                /*
                xTopLeft = xTopLeft + (cos * xTopLeft - sin * yTopLeft);
                yTopLeft = yTopLeft + (sin * xTopLeft - cos * yTopLeft);
                
                xTopRight = xTopRight + (cos * xTopRight - sin * yTopRight);
                yTopRight = yTopRight + (sin * xTopRight - cos * yTopRight);
                
                xBottomRight = xBottomRight + (cos * xBottomRight - sin * yBottomRight);
                yBottomRight = yBottomRight + (sin * xBottomRight - cos * yBottomRight);
                
                xBottomLeft = xBottomLeft + (cos * xBottomLeft - sin * yBottomLeft);
                yBottomLeft = yBottomLeft + (sin * xBottomLeft - cos * yBottomLeft);
                
                xTopLeft = xTopLeft + x;
                yTopLeft = yTopLeft + y;
                
                xTopRight = xTopRight + x;
                yTopRight = yTopRight + y;
                
                xBottomRight = xBottomRight + x;
                yBottomRight = yBottomRight + y;
                
                xBottomLeft = xBottomLeft + x;
                yBottomLeft = yBottomLeft + y;
                
                */
                
                /*
                x1 = x + (cos * p1x - sin * p1y) + cx; // TOP LEFT
                y1 = y + (sin * p1x + cos * p1y) + cy;
                x2 = x + (cos * p2x - sin * p2y) + cx; // TOP RIGHT
                y2 = y + (sin * p2x + cos * p2y) + cy;
                x3 = x + (cos * p3x - sin * p3y) + cx; // BOTTOM RIGHT
                y3 = y + (sin * p3x + cos * p3y) + cy;
                x4 = x + (cos * p4x - sin * p4y) + cx; // BOTTOM LEFT
                y4 = y + (sin * p4x + cos * p4y) + cy;
                */
                
                /*
                // top left
                data[0] = xTopLeft;
                data[1] = yTopLeft;
                data[2] = Color.WHITE.r;
                data[3] = Color.WHITE.g;
                data[4] = Color.WHITE.b;
                data[5] = Color.WHITE.a;
                data[6] = 0.0f;
                data[7] = 0.0f;
            
                // top right
                data[8] = xTopRight;
                data[9] = yTopRight;
                data[10] = Color.WHITE.r;
                data[11] = Color.WHITE.g;
                data[12] = Color.WHITE.b;
                data[13] = Color.WHITE.a;
                data[14] = 1.0f;
                data[15] = 0.0f;
                
                // bottom left
                data[16] = xBottomLeft;
                data[17] = yBottomLeft;
                data[18] = Color.WHITE.r;
                data[19] = Color.WHITE.g;
                data[20] = Color.WHITE.b;
                data[21] = Color.WHITE.a;
                data[22] = 0.0f;
                data[23] = 1.0f;
                
                // top right
                data[24] = xTopRight;
                data[25] = yTopRight;
                data[26] = Color.WHITE.r;
                data[27] = Color.WHITE.g;
                data[28] = Color.WHITE.b;
                data[29] = Color.WHITE.a;
                data[30] = 1.0f;
                data[31] = 0.0f;
                
                // bottom right
                data[32] = xBottomRight;
                data[33] = yBottomRight;
                data[34] = Color.WHITE.r;
                data[35] = Color.WHITE.g;
                data[36] = Color.WHITE.b;
                data[37] = Color.WHITE.a;
                data[38] = 1.0f;
                data[39] = 1.0f;
                
                // bottom left
                data[40] = xBottomLeft;
                data[41] = yBottomLeft;
                data[42] = Color.WHITE.r;
                data[43] = Color.WHITE.g;
                data[44] = Color.WHITE.b;
                data[45] = Color.WHITE.a;
                data[46] = 0.0f;
                data[47] = 1.0f;
            
                Renderer.get().draw(attackArea, data, 0);

            }
        }
        */
        super.render();
        
        for(WeaponLocationDisplay wld : weaponLocDisplays) {
            wld.render();
        }
    }
    
    @Override
    public void update(long deltaMs) {
        super.update(deltaMs);
        
        if(!locsUpdated) {
            updateWeaponLocs(deltaMs);
        }
    }
    
    public void updateWeaponLocs(long deltaMs) {
        weaponLocDisplays.clear();
        for(WeaponLocation loc : Application.get().getLogic().getGame().getWeaponLocations()) {
            weaponLocDisplays.add(new WeaponLocationDisplay(loc));
        }
        
        locsUpdated = true;
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(super.onMouseDown(mX, mY, button)) {
            return true;
        }
        
        for(WeaponLocationDisplay wld : weaponLocDisplays) {
            if(wld.onMouseDown(mX, mY, button)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean onKeyDown(int key) {
        if(super.onKeyDown(key)) {
            return true;
        }
        
        if(key == Keyboard.KEY_TAB) {
            Application.get().getHumanView().addScreen(ConsoleFactory.create());
            return true;
        }
        else if(key == Keyboard.KEY_ESCAPE) {
            Application.get().getEventManager().queueEvent(new GameStateChangeEvent(GameLogic.GameState.PAUSED));
            Application.get().getHumanView().addScreen(PauseScreenFactory.create());
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY) {
        if(super.onPointerMove(mX, mY, mDX, mDY)) {
            return true;
        }
        
        targetX = mX;
        targetY = mY;
        
        return false;
    }
}
