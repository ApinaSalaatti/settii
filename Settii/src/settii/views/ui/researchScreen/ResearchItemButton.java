package settii.views.ui.researchScreen;

import settii.Application;
import settii.eventManager.events.AttemptToBuyEvent;
import settii.logic.research.ResearchItem;
import settii.views.humanView.renderer.Renderer;
import settii.views.humanView.renderer.Texture;
import settii.views.ui.BaseScreenItem;
/**
 *
 * @author Merioksan Mikko
 */
public class ResearchItemButton extends BaseScreenItem {
    ResearchItem item;
    
    public ResearchItemButton(float x, float y, ResearchItem i) {
        super(x, y, 60, 60, "assets/graphics/ui/researchscreen/researchItem.png");
        item = i;
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        super.onMouseDown(mX, mY, button);
        if(button == 0 && mX > x && mX < x + width && mY > y && mY < y + height) {
            Application.get().getEventManager().queueEvent(new AttemptToBuyEvent(item));
            return true;
        }
        return false;
    }
    
    @Override
    public void render() {
        super.render();
        if(item != null) {
            Texture tex = Application.get().getResourceManager().getTextureManager().getTexture(item.getSprite());
            Renderer.get().draw(tex, x, y);
            
            Renderer.get().drawText("Cost:" + item.getValue(), x, y+70, 0.5f);
        }
    }
}
