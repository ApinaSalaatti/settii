package settii.views.ui.commandCenterScreen;

import settii.Application;
import settii.eventManager.events.AttemptRepairEvent;
import settii.views.humanView.renderer.Renderer;
import settii.views.ui.BaseScreenItem;

/**
 *
 * @author Merioksan Mikko
 */
public class RepairBaseButton extends BaseScreenItem {

    public RepairBaseButton(float x, float y) {
        super(x, y, 120, 120, "assets/graphics/ui/commandcenterscreen/repairBaseButton.png");
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(super.onMouseDown(mX, mY, button)) {
            return true;
        }
        if(mX > x && mX < x + width && mY > y && mY < y + height) {
            Application.get().getEventManager().queueEvent(new AttemptRepairEvent());
        }
        
        return false;
    }
    
    @Override
    public void render() {
        super.render();
        Renderer.get().drawText("Current cost:" + Application.get().getLogic().getGame().getShop().getBaseRepairCost(), x+120, y+50);
    }
}
