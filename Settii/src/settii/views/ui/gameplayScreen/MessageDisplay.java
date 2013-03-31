package settii.views.ui.gameplayScreen;

import settii.Application;
import settii.logic.messaging.Message;
import settii.views.humanView.renderer.Renderer;
import settii.views.humanView.renderer.Texture;
import settii.views.ui.BaseScreenItem;

/**
 *
 * @author Merioksan Mikko
 */
public class MessageDisplay extends BaseScreenItem {
    public int maxLength;
    
    public MessageDisplay(float x, float y) {
        super(x, y, 480, 180, "assets/graphics/ui/gameplayscreen/priorityMessage.png");
        maxLength = 440;
    }
    
    @Override
    public void render() {
        Message m = Application.get().getLogic().getGame().getCurrentMessage();
        if(m != null) {
            if(m.priority()) {
                super.render();
                Renderer.get().drawText(m.getTopic(), x+38, y+5);
                int lineLength = maxLength / 12;
                if(m.getContent().length() < lineLength) {
                    Renderer.get().drawText(m.getContent(), x+38, y+40, 0.75f);
                }
                else {
                    renderManyLines(m.getContent(), lineLength, 0);
                }
            }
            else {
                Renderer.get().drawText(m.getTopic(), x+5, y+5, 0.5f);
                Renderer.get().drawText(m.getContent(), x+5, y+23, 0.5f);
            }
        }
    }
    
    public void renderManyLines(String text, int lineLength, int line) {
        if(text.length() <= lineLength) {
            Renderer.get().drawText(text, x+38, y+40+line*26, 0.75f);
        }
        else {
            int cut = findSpace(text, lineLength);
            Renderer.get().drawText(text.substring(0, cut+1), x+38, y+40+line*26, 0.75f);
            renderManyLines(text.substring(cut+1, text.length()), lineLength, line+1);
        }
    }
    
    public int findSpace(String text, int lineLength) {
        for(int i = lineLength-1; i >= 0; i--) {
            if(text.charAt(i) == ' ') {
                return i;
            }  
        }
        
        return 0;
    }
}
