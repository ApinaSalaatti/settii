package settii.logic;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import settii.Application;
import settii.actorManager.GameActor;
import settii.views.ui.IScreenItem;

import org.lwjgl.opengl.Display;
/**
 *
 * @author Merioksan Mikko
 */
public class LevelLogic {
    
    public LevelLogic() {
        // cool testing stuff
        long id2 = Application.get().getLogic().createActor("assets/data/actors/cannon.xml");
        GameActor act2 = Application.get().getLogic().getActor(id2);
        act2.move(Display.getWidth() / 2, Display.getHeight() - 50);
    }
    public LevelLogic(String resource) {
        
    }
    
    public void update(long deltaMs) {
        
    }
}
