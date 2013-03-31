package settii.logic.mouse;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import settii.Application;
import settii.actorManager.GameActor;
import settii.actorManager.components.*;
import settii.eventManager.events.ActorDestroyedEvent;
import settii.views.humanView.renderer.Renderer;
/**
 *
 * @author Merioksan Mikko
 */
public class PlaceWeaponAction implements IMouseAction {
    public static float placementAreaX = Display.getWidth() - 200;
    public static float placementAreaY = Display.getHeight() - 200;
    public static float placementAreaWidth = Display.getWidth() + 400;
    public static float placementAreaHeight = Display.getHeight() + 400;
    private GameActor actor;
    private int cost;
    
    public PlaceWeaponAction(GameActor actor, int cost) {
        actor.disable();
        this.actor = actor;
        this.cost = cost;
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(button == 0) {
            Application.get().getLogic().getGame().addPlayerWeapon(actor.getID());

            //PhysicsComponent pc = (PhysicsComponent)actor.getComponent("PhysicsComponent");
            //pc.setLocation(mX, mY);
            actor.enable();
            Application.get().getLogic().getGame().setCurrentMouseAction(new DefaultAction());
        }
        else if(button == 1) {
            InventoryComponent inv = Application.get().getLogic().getGame().getPlayer().getInventory();
            inv.addMoney(cost);
            Application.get().getEventManager().queueEvent(new ActorDestroyedEvent(actor));
            Application.get().getLogic().getGame().setCurrentMouseAction(new DefaultAction());
        }
        
        return false;
    }
    
    @Override
    public boolean onMouseUp(int mX, int mY, int button) {
        return false;
    }
    
    @Override
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY) {
        PhysicsComponent pc = (PhysicsComponent)actor.getComponent("PhysicsComponent");
        float X = pc.getX();
        float Y = pc.getY();
        
        if(mX < placementAreaX) {
            X = placementAreaX;
        }
        else if(mX > placementAreaX + placementAreaWidth) {
            X = placementAreaX + placementAreaWidth;
        }
        else {
            X = mX;
        }
        
        if(mY < placementAreaY) {
            Y = placementAreaY;
        }
        else if(mY > placementAreaY + placementAreaHeight) {
            Y = placementAreaY + placementAreaHeight;
        }
        else {
            Y = mY;
        }
        
        actor.move(X, Y);
        return false;
    }
    
    @Override
    public void update(long deltaMs) {
        
    }
}
