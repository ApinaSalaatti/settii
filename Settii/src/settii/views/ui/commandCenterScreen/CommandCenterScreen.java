package settii.views.ui.commandCenterScreen;

import java.util.Iterator;
import settii.views.ui.BaseGameScreen;
import org.lwjgl.input.Keyboard;
import settii.Application;
import settii.views.humanView.renderer.Renderer;
import settii.views.ui.IScreenItem;
import settii.actorManager.GameActor;
import settii.actorManager.components.PhysicsComponent;
/**
 *
 * @author Merioksan Mikko
 */
public class CommandCenterScreen extends BaseGameScreen {
    int i;
    boolean b;
    
    public CommandCenterScreen() {
        i = 0;
        b = true;
    }
    @Override
    public boolean onKeyDown(int key) {
        if(key == Keyboard.KEY_C || key == Keyboard.KEY_ESCAPE) {
            Application.get().getHumanView().popScreen();
            return true;
        }
        
        return true;
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        super.onMouseDown(mX, mY, button);
        return true;
    }
    
    @Override
    public void render() {
        GameActor a = Application.get().getLogic().getActor(Application.get().getLogic().getGame().getBaseID());
        PhysicsComponent pc = null;
        if(a != null) {
            pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
        }
        
        if(pc != null && (float)((float)pc.getHealth() / (float)pc.getMaxHealth()) <= 0.3) {
            renderCrisis();
        }
        else {
            super.render();
        }
    }
    
    public void renderCrisis() {
        if(b)
            i++;
        else {
            i--;
        }
        if(i >= 100) {
            b = false;
        }
        else if(i <= 0) {
            b = true;
        }
        
        Iterator<IScreenItem> it = screenItems.descendingIterator();
        boolean bgDone = false;
        while(it.hasNext()) {
            IScreenItem item = it.next();
            if(!bgDone) {
                Renderer.get().getSpriteBatch().setColor(1.0f, 1.0f-(float)i / 100f, 1.0f-(float)i / 100f, 1.0f);
            }
            if(item.isVisible()) {
                item.render();
            }
            if(!bgDone) {
                bgDone = true; // bg is first
                Renderer.get().getSpriteBatch().setColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        
    }
}
