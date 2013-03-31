package settii.views.ui.console;

import org.lwjgl.input.Keyboard;
import settii.Application;
import settii.views.ui.BaseGameScreen;
/**
 *
 * @author Merioksan Mikko
 */
public class Console extends BaseGameScreen {
    private TextDisplay td;
    
    public Console() {
        super();
        td = new TextDisplay();
    }

    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        if(super.onMouseDown(mX, mY, button)) {
            return true;
        }
        
        Application.get().getHumanView().popScreen();
        return true;
    }
    
    @Override
    public boolean onKeyDown(int key) {
        if(super.onKeyDown(key)) {
            return true;
        }
        
        if(key == Keyboard.KEY_ESCAPE || key == Keyboard.KEY_TAB) {
            Application.get().getHumanView().popScreen();
            return true;
        }
        
        return true;
    }
    
    public void processInput(String input) {
        String[] words = input.split(" ");
        if(words[0].equalsIgnoreCase("clear")) {
            td.clear();
        }
        else if(words[0].equalsIgnoreCase("disablecomp")) {
            Application.get().getLogic().disableComponent(words[1]);
        }
        else if(words[0].equalsIgnoreCase("enablecomp")) {
            Application.get().getLogic().enableComponent(words[1]);
        }
        else if(words[0].equalsIgnoreCase("volume")) {
            float vol = Float.parseFloat(words[1]);
            Application.get().getOptions().setVolume(vol);
            Application.get().getOptions().save();
        }
        
        if(!input.equals("")) {
            td.addText(input);
        }
    }
    
    @Override
    public void render() {
        super.render();
        td.render();
    }
}
