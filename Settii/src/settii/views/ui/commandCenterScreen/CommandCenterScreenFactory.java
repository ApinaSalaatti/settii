package settii.views.ui.commandCenterScreen;

import settii.views.ui.*;
import settii.views.ui.screenItems.*;
import settii.logic.GameLogic;
import org.lwjgl.opengl.Display;
/**
 *
 * @author Merioksan Mikko
 */
public class CommandCenterScreenFactory {
    public CommandCenterScreenFactory() {
        
    }
    
    public static CommandCenterScreen create() {
        CommandCenterScreen ccs = new CommandCenterScreen();
        
        ccs.addScreenItem(new Background());
        ccs.addScreenItem(new ResearchButton(30, (Display.getHeight() - MapDisplay.HEIGHT) / 2));
        ccs.addScreenItem(new PopScreenButton(25, (Display.getHeight() - MapDisplay.HEIGHT) / 2 + 120, 60, 60, "assets/graphics/ui/resume.png"));
        ccs.addScreenItem(new GameStateChangeButton(25, (Display.getHeight() - MapDisplay.HEIGHT) / 2 + 190, 60, 60, GameLogic.GameState.QUITTING, "assets/graphics/ui/quit.png"));
        ccs.addScreenItem(new MapDisplay((Display.getWidth() - MapDisplay.WIDTH) / 2, (Display.getHeight() - MapDisplay.HEIGHT) / 2));
        ccs.addScreenItem(new StatusDisplay());

        return ccs;
    }
}
