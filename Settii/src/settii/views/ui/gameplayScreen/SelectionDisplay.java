package settii.views.ui.gameplayScreen;

import java.util.ArrayList;
import settii.Application;
import settii.views.ui.BaseScreenItem;
import settii.actorManager.GameActor;
import settii.actorManager.components.*;
import settii.views.humanView.renderer.Renderer;
/**
 *
 * @author Merioksan Mikko
 */
public class SelectionDisplay extends BaseScreenItem {
    public SelectionDisplay(float x, float y) {
        super(x, y, 200, 200, "assets/graphics/ui/gameplayscreen/selectionDisplay.png");
    }
    
    @Override
    public void render() {
        super.render();
        
        ArrayList<Long> selected = Application.get().getLogic().getGame().getSelectedWeapons();
        if(selected.size() > 0) {
            if(selected.size() > 1) {
                int i = 0;
                for(long a : Application.get().getLogic().getGame().getSelectedWeapons()) {
                    GameActor actor = Application.get().getLogic().getActor(a);
                    StatusComponent sc = (StatusComponent)actor.getComponent("StatusComponent");
                    Renderer.get().drawText(sc.getActorName(), x+40, y+50+i*20, 0.5f);
                    i++;
                }
            }
            else {
                GameActor actor = Application.get().getLogic().getActor(selected.get(0));
                StatusComponent sc = (StatusComponent)actor.getComponent("StatusComponent");
                WeaponsComponent wc = (WeaponsComponent)actor.getComponent("WeaponsComponent");
                PhysicsComponent pc = (PhysicsComponent)actor.getComponent("PhysicsComponent");
                
                Renderer.get().drawText(sc.getActorName(), x+40, y+50, 0.5f);
                Renderer.get().drawText("Health: " + pc.getHealth(), x+40, y+70, 0.5f);
                Renderer.get().drawText("Damage: " + wc.getDamage(), x+40, y+90, 0.5f);
            }
        }
    }
}
