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
    private GameActor actor;
    private int cost;
    
    public PlaceWeaponAction(GameActor actor, int cost) {
        this.actor = actor;
        this.cost = cost;
    }
    
    @Override
    public void execute(int mX, int mY, int button) {
        if(button == 0) {
            InventoryComponent inv = Application.get().getLogic().getGame().getPlayer().getInventory();
            inv.removeMoney(cost);
            Application.get().getLogic().getGame().addPlayerWeapon(actor.getID());

            PhysicsComponent pc = (PhysicsComponent)actor.getComponent("PhysicsComponent");
            pc.setLocation(mX, mY);
            Application.get().getLogic().getGame().setCurrentMouseAction(new DefaultAction());
        }
        else if(button == 1) {
            Application.get().getEventManager().queueEvent(new ActorDestroyedEvent(actor));
            Application.get().getLogic().getGame().setCurrentMouseAction(new DefaultAction());
        }
    }
    
    @Override
    public void update(long deltaMs) {
        // TODO: I don't like this, should create own Mouse class to wrap all this crap somehow!
        float x = Mouse.getX();
        float y = Display.getHeight() - Mouse.getY();
        
        actor.move(x, y);
    }
    
    @Override
    public void render() {
        
    }
}
