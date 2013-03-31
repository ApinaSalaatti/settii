package settii.views.ui.gameplayScreen;

import settii.Application;
import settii.views.ui.BaseScreenItem;
import settii.actorManager.GameActor;
import settii.actorManager.components.InventoryComponent;
import settii.actorManager.components.PhysicsComponent;
import settii.views.humanView.renderer.Renderer;
import settii.logic.Player;
/**
 *
 * @author Merioksan Mikko
 */
public class StatusDisplay extends BaseScreenItem {
    public StatusDisplay(float x, float y, float w, float h) {
        super(x, y, w, h, null);
    }
    
    @Override
    public void render() {
        super.render();
        Player player = Application.get().getLogic().getGame().getPlayer();
        InventoryComponent ic = player.getInventory();

        Renderer.get().drawText("Money:" + ic.getMoney(), x + 5, y + 5);
        
        /*
        Renderer.get().drawText("Crap:", x + 200, y + 5);
        Renderer.get().drawText(ic.getCrap() + "", x + 200 + 5*16, y + 5);
        
        Renderer.get().drawText("Cum:", x + 400, y + 5);
        Renderer.get().drawText(ic.getCum() + "", x + 400 + 4*16, y + 5);
        */
        
        Renderer.get().drawText("Score:" + player.getExp(), x + 250, y + 5);
        
        long bID = Application.get().getLogic().getGame().getBaseID();
        GameActor b = Application.get().getLogic().getActor(bID);
        
        if(b != null) {
            PhysicsComponent bPC = (PhysicsComponent)b.getComponent("PhysicsComponent");
            Renderer.get().drawText("Vaginal health:" + bPC.getHealth(), x+450, y+5);
        }
        
        //if(Application.get().getLogic().getGame().getCurrentSector() != null) {
        //    Renderer.get().drawText(Application.get().getLogic().getGame().getCurrentSector().getName(), x + 5, y + 40);
        //}
        
        Renderer.get().drawText("Time:" + Application.get().getLogic().getGame().getTime() / 1000, x + 5, y + 40);
    }
}
