package settii.views.ui.gameplayScreen;


import org.lwjgl.opengl.Display;
import settii.Application;
import settii.logic.WeaponLocation;
/**
 *
 * @author Merioksan Mikko
 */
public class GameplayScreenFactory {
    public GameplayScreenFactory() {
        
    }
    
    public static GameplayScreen create() {
        GameplayScreen screen = new GameplayScreen();
        
        screen.addScreenItem(new ShopButton(10, Display.getHeight() - 100, 104, 88));
        screen.addScreenItem(new ResearchButton(120, Display.getHeight() - 100));
        screen.addScreenItem(new StatusDisplay(0, 0, 40, Display.getWidth()));
        screen.addScreenItem(new SelectionDisplay(Display.getWidth() - 250, Display.getHeight() - 250));
        //screen.addScreenItem(new CommandCenterButton(120, Display.getHeight() - 120));
        screen.addScreenItem(new MessageDisplay(10, 100));
        
        //for(WeaponLocation wl : Application.get().getLogic().getGame().getWeaponLocations()) {
        //    screen.addScreenItem(new WeaponLocationDisplay(wl));
        //}
        
        return screen;
    }
}
