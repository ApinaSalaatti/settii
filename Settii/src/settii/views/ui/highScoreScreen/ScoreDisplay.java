package settii.views.ui.highScoreScreen;

import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import settii.HighScores;
import settii.Score;
import settii.views.humanView.renderer.Renderer;
import settii.views.ui.BaseScreenItem;

/**
 *
 * @author Merioksan Mikko
 */
public class ScoreDisplay extends BaseScreenItem {
    private ArrayList<Score> scores;
    
    public ScoreDisplay() {
        super((Display.getWidth() / 2) - 150, (Display.getHeight() / 2) - 200, 300, 400, "assets/graphics/ui/highscorescreen/scoreDisplay.png");
        
        scores = HighScores.get();
    }
    
    public void updateScores(ArrayList<Score> s) {
        scores = s;
    }
    
    @Override
    public void render() {
        super.render();
        
        if(scores != null) {
            int indx = 0;
            for(Score s : scores) {
                Renderer.get().drawText(s.toString(), x+30, y+30+indx*35);
                indx++;
            }
        }
        else {
            Renderer.get().drawText("Connection failed!", x+10, y+30);
        }
    }
    
    public void updateScores() {
        scores = HighScores.get();
    }
}
