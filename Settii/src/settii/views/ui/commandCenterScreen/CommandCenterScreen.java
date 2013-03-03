package settii.views.ui.commandCenterScreen;

import settii.views.ui.BaseGameScreen;
import org.lwjgl.input.Keyboard;
import settii.Application;
import settii.views.humanView.renderer.Renderer;
/**
 *
 * @author Merioksan Mikko
 */
public class CommandCenterScreen extends BaseGameScreen {

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
        super.render();
    }
}
