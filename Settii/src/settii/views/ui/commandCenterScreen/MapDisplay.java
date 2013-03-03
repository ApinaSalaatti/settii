package settii.views.ui.commandCenterScreen;

import java.util.ArrayList;
import java.util.Collection;
import settii.Application;
import settii.views.ui.BaseScreenItem;
import settii.actorManager.GameActor;
import settii.actorManager.components.*;
import settii.views.humanView.renderer.Renderer;
import settii.views.humanView.renderer.Texture;
import org.lwjgl.opengl.Display;
import settii.eventManager.events.ChangeSectorEvent;
import settii.logic.sectors.*;
/**
 *
 * @author Merioksan Mikko
 */
public class MapDisplay extends BaseScreenItem {
    public static int WIDTH = 512;
    public static int HEIGHT = 410;
    
    private Texture baseHealthy, baseDamaged, baseCritical;
    private Texture friendly;
    private Texture friendlyProj;
    private Texture enemy;
    private Texture enemyProj;
    
    public MapDisplay(int x, int y) {
        super(x, y, WIDTH, HEIGHT, "assets/graphics/ui/commandcenterscreen/mapDisplay.png");
        
        baseHealthy = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/baseHealthy.png");
        baseDamaged = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/baseDamaged.png");
        baseCritical = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/baseCritical.png");
        friendly = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/friendly.png");
        friendlyProj = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/friendlyProj.png");
        enemy = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/enemy.png");
        enemyProj = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/commandcenterscreen/enemyProj.png");
    }
    
    @Override
    public void render() {
        super.render();
        
        //Renderer.get().draw(base, (x + WIDTH /2) - baseXOffset, (y + HEIGHT / 2) - baseYOffset);
        
        Collection<GameActor> actors = Application.get().getLogic().getActors();
        
        for(GameActor a : actors) {
            StatusComponent sc = (StatusComponent)a.getComponent("StatusComponent");
            PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
            
            if(pc != null && sc != null && sc.getAllegiance().equals("enemy")) {
                float aX = (float)pc.getX() * (float)((float)(WIDTH - 20) / (Display.getWidth() * 3.0f));
                float aY = (float)pc.getY() * (float)((float)(HEIGHT - 20) / (Display.getHeight() * 3.0f));
                if(sc.getType().equals("projectile")) {
                    Renderer.get().draw(enemyProj, 5+x+aX, 5+y+aY);
                }
                else {
                    Renderer.get().draw(enemy, 5+x+aX, 5+y+aY);
                }
            }
            else if(pc != null && sc != null && sc.getAllegiance().equals("friendly")) {
                float aX = (float)pc.getX() * (float)((float)(WIDTH - 20) / (Display.getWidth() * 3.0f));
                float aY = (float)pc.getY() * (float)((float)(HEIGHT - 20) / (Display.getHeight() * 3.0f));
                if(sc.getType().equals("projectile")) {
                    Renderer.get().draw(friendlyProj, 5+x+aX, 5+y+aY);
                }
                else if(sc.getType().equals("base")) {
                    float healthPercent = (float)((float)pc.getHealth() / (float)pc.getMaxHealth());
                    float baseXOffset = baseHealthy.getWidth() / 2;
                    float baseYOffset = baseHealthy.getHeight() / 2;
                    renderBase(healthPercent, 5+x+aX-baseXOffset, 5+y+aY-baseYOffset);
                    //Renderer.get().draw(base, 5+x + aX - baseXOffset, 5+y + aY - baseYOffset);
                }
                else {
                    Renderer.get().draw(friendly, 5+x+aX, 5+y+aY);
                }
            }
        }
    }
    
    public void renderBase(float health, float x, float y) {
        if(health > 0.7) {
            Renderer.get().draw(baseHealthy, x, y);
        }
        else if(health > 0.3) {
            Renderer.get().draw(baseDamaged, x, y);
        }
        else {
            Renderer.get().draw(baseCritical, x, y);
        }
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        // TODO: wrap this crap somewhere...
        int northX = 175;
        int northY = 0;
        int eastX = 341;
        int eastY = 128;
        int southX = 175;
        int southY = 260;
        int westX = 10;
        int westY = 128;
        int sWidth = 160;
        int sHeight = 142;
        
        if(button == 0) {
            if(mX > x + northX && mX < x + northX + sWidth && mY > y + northY && mY < y + northY + sHeight) {
                Application.get().getEventManager().queueEvent(new ChangeSectorEvent(NorthSector.ID));
                Application.get().getHumanView().popScreen();
            }
            else if(mX > x + eastX && mX < x + eastX + sWidth && mY > y + eastY && mY < y + eastY + sHeight) {
                Application.get().getEventManager().queueEvent(new ChangeSectorEvent(EastSector.ID));
                Application.get().getHumanView().popScreen();
            }
            else if(mX > x + southX && mX < x + southX + sWidth && mY > y + southY && mY < y + southY + sHeight) {
                Application.get().getEventManager().queueEvent(new ChangeSectorEvent(SouthSector.ID));
                Application.get().getHumanView().popScreen();
            }
            else if(mX > x + westX && mX < x + westX + sWidth && mY > y + westY && mY < y + westY + sHeight) {
                Application.get().getEventManager().queueEvent(new ChangeSectorEvent(WestSector.ID));
                Application.get().getHumanView().popScreen();
            }
        }
        
        return false;
    }
}
