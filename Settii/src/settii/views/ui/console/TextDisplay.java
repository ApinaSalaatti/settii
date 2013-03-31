package settii.views.ui.console;

import java.util.ArrayList;
import java.util.Iterator;
import settii.views.humanView.renderer.Renderer;
import settii.views.ui.BaseScreenItem;
/**
 *
 * @author Merioksan Mikko
 */
public class TextDisplay extends BaseScreenItem {
    private ArrayList<String> inputHistory;
    private int maxLines;
    
    public TextDisplay() {
        super(10, 10, 512, 512, "assets/graphics/ui/console/textDisplay.png");
        inputHistory = new ArrayList<String>();
        maxLines = 29;
    }
    
    public void addText(String t) {
        inputHistory.add(t);
    }
    
    @Override
    public void render() {
        super.render();
        int indx = 0;
        int offset = 0;
        if(overflow() > 0) {
            offset = overflow();
        }
        
        Iterator<String> it = inputHistory.iterator();
        while(offset > 0) {
            it.next();
            offset--;
        }
        
        while(it.hasNext()) {
            String s = it.next();
            Renderer.get().drawText(s, x+8, y+8+indx*17, 0.5f);
            indx++;
        }
    }
    
    public int overflow() {
        return inputHistory.size() - maxLines; // max visible lines on console is 29
    }
    
    public void clear() {
        inputHistory.clear();
    }
}
