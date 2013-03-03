package settii.views.ui.commandCenterScreen;

import settii.views.ui.BaseScreenItem;
import org.lwjgl.opengl.Display;
/**
 *
 * @author Merioksan Mikko
 */
public class Background extends BaseScreenItem {
    public Background() {
        super(0, 0, Display.getWidth(), Display.getHeight(), "assets/graphics/ui/commandcenterscreen/background.png");
    }
}
