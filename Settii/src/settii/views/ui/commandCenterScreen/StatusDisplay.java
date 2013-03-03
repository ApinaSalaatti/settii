package settii.views.ui.commandCenterScreen;

import settii.Application;
import settii.views.humanView.renderer.Renderer;
import settii.views.ui.BaseScreenItem;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
/**
 *
 * @author Merioksan Mikko
 */
public class StatusDisplay extends BaseScreenItem {
    public StatusDisplay() {
        super(0, 0, 400, 100, null);
    }
    
    @Override
    public void render() {
        super.render();
        
        long bID = Application.get().getLogic().getGame().getBaseID();
        GameActor b = Application.get().getLogic().getActor(bID);
        
        if(b != null) {
            PhysicsComponent bPC = (PhysicsComponent)b.getComponent("PhysicsComponent");
            Renderer.get().drawText(bPC.getHealth() + "", x+180, y+40);
        }
    }
}
