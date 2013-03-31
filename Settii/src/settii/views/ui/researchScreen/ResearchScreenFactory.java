package settii.views.ui.researchScreen;

import settii.Application;
import settii.logic.research.*;
import settii.views.ui.screenItems.PopScreenButton;
import java.util.Collection;
import org.lwjgl.opengl.Display;
/**
 *
 * @author Merioksan Mikko
 */
public class ResearchScreenFactory {
    public static ResearchScreen create() {
        ResearchScreen rs = new ResearchScreen();
        
        rs.addScreenItem(new Background((Display.getWidth() - Background.WIDTH) / 2, (Display.getHeight() - Background.HEIGHT) / 2));
        
        createTrees(rs);
        
        rs.addScreenItem(new PopScreenButton(Display.getWidth() - 35, 20, 30, 30, "assets/graphics/ui/xButton.png"));
        
        return rs;
    }
    
    public static void createTree(ResearchScreen rs, ResearchItem root, float x, float y) {
        if(root != null) {
            rs.addItemSlot(x, y, root);
            if(root.researched()) {
                int indx = 0;
                for(ResearchItem i : root.getFollowers()) {
                    createTree(rs, i, x+indx*90, y+90);
                    indx++;
                }
            }
        }
    }
    
    public static void createTrees(ResearchScreen rs) {
        Collection<ResearchItem> items = Application.get().getLogic().getGame().getResearch().getRoots();
        int indx = 0;
        for(ResearchItem ri : items) {
            createTree(rs, ri, 100+indx*180, 100);
            indx++;
        }
    }
}
