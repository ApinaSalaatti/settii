package settii.views.ui;

import java.util.ArrayDeque;
import settii.Application;
import settii.views.humanView.renderer.Renderer;
import settii.views.humanView.renderer.Texture;
/**
 *
 * @author Merioksan Mikko
 */
public class Tooltip {
    private float x, y;
    private ArrayDeque<String> lines;
    private Texture bg;
    
    public Tooltip() {
        lines = new ArrayDeque<String>();
        bg = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/tooltipBG.png");
    }
    
    public void set(String s) {
        String line = "";
        boolean toChangeLine = false;
        
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '\n') {
                lines.addLast(line);
                line = "";
            }
            else {
                line += c;
            }
        }
        lines.addLast(line);
    }
    
    public void addLine(String line) {
        lines.addLast(line);
    }
    
    public void setLocation(float X, float Y) {
        x = X;
        y = Y;
    }
    
    public void render() {
        int indx = 0;
        int longest = 0;
        for(String line : lines) {
            if(line.length() > longest) {
                longest = line.length();
            }
        }
        for(String line : lines) {
            for(int i = 0; i < longest; i++) {
                Renderer.get().draw(bg, x+i*Renderer.get().getFont().getLetterWidth()*0.5f, y+indx*Renderer.get().getFont().getLetterHeight()*0.5f, Renderer.get().getFont().getLetterWidth()*0.5f, Renderer.get().getFont().getLetterHeight()*0.5f);
            }
            Renderer.get().drawText(line, x, y+indx*Renderer.get().getFont().getLetterHeight()*0.5f, 0.5f);
            indx++;
        }
    }
}
