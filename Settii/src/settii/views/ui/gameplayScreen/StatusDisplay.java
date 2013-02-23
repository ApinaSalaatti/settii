package settii.views.ui.gameplayScreen;

import settii.Application;
import settii.views.ui.BaseScreenItem;
import settii.actorManager.GameActor;
import settii.actorManager.components.InventoryComponent;
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

        Renderer.get().drawText("Money:", x + 5, y + 5);
        Renderer.get().drawText(ic.getMoney() + "", x + 5 + 6*16, y + 5);

        Renderer.get().drawText("Crap:", x + 200, y + 5);
        Renderer.get().drawText(ic.getCrap() + "", x + 200 + 5*16, y + 5);
        
        Renderer.get().drawText("Cum:", x + 400, y + 5);
        Renderer.get().drawText(ic.getCum() + "", x + 400 + 4*16, y + 5);
        
        Renderer.get().drawText("Exp:", x + 600, y + 5);
        Renderer.get().drawText(player.getExp() + "", x + 600 + 4*16, y + 5);
    }
}
