package settii.views.ui.highScoreScreen;

import org.lwjgl.input.Keyboard;
import settii.Application;
import settii.HighScores;
import settii.logic.Player;
import settii.views.humanView.renderer.Renderer;
import settii.views.ui.BaseScreenItem;

/**
 *
 * @author Merioksan Mikko
 */
public class ScoreInput extends BaseScreenItem {
    public static char INVALID_CHAR = (char)-1;
    private String currentInput;
    private boolean scoreSent;
    
    public ScoreInput() {
        super(100, 100, 300, 50, null);
        currentInput = "";
        scoreSent = false;
    }
    
    public boolean scoreSent() {
        return scoreSent;
    }
    
    @Override
    public void render() {
        super.render();
        
        if(!scoreSent) {
            Player player = Application.get().getLogic().getGame().getPlayer();
            Renderer.get().drawText("Your score:" + player.getExp(), x+5, y+5);
            Renderer.get().drawText("Your initials:" + currentInput, x+5, y+40);
        }
    }
    
    @Override
    public boolean onKeyDown(int key) {
        super.onKeyDown(key);

        if(key == Keyboard.KEY_RETURN && !scoreSent) {
            scoreSent = true;
            Player player = Application.get().getLogic().getGame().getPlayer();
            HighScores.send(currentInput, player.getExp());
            return true;
        }
        else if(key == Keyboard.KEY_BACK && currentInput.length() > 0 && !scoreSent) {
            currentInput = currentInput.substring(0, currentInput.length()-1);
        }
        else if(getChar(key) != INVALID_CHAR && !scoreSent) {
            currentInput += getChar(key);
            if(currentInput.length() > 3) {
                currentInput = currentInput.substring(0, 3);
            }
        }

        return false;
    }
    
    public char getChar(int key) {
        switch(key) {
            case Keyboard.KEY_A: return 'a';
            case Keyboard.KEY_B: return 'b';
            case Keyboard.KEY_C: return 'c';
            case Keyboard.KEY_D: return 'd';
            case Keyboard.KEY_E: return 'e';    
            case Keyboard.KEY_F: return 'f';    
            case Keyboard.KEY_G: return 'g'; 
            case Keyboard.KEY_H: return 'h';
            case Keyboard.KEY_I: return 'i';
            case Keyboard.KEY_J: return 'j';
            case Keyboard.KEY_K: return 'k';
            case Keyboard.KEY_L: return 'l';
            case Keyboard.KEY_M: return 'm';
            case Keyboard.KEY_N: return 'n';
            case Keyboard.KEY_O: return 'o';
            case Keyboard.KEY_P: return 'p';
            case Keyboard.KEY_Q: return 'q';
            case Keyboard.KEY_R: return 'r';
            case Keyboard.KEY_S: return 's';
            case Keyboard.KEY_T: return 't';
            case Keyboard.KEY_U: return 'u';
            case Keyboard.KEY_V: return 'v';
            case Keyboard.KEY_W: return 'w';
            case Keyboard.KEY_X: return 'x';
            case Keyboard.KEY_Y: return 'y';
            case Keyboard.KEY_Z: return 'z';
            case 27: return 'å';
            case 40: return 'ä';
            case 41: return 'ö';
            case Keyboard.KEY_1: return '1';
            case Keyboard.KEY_2: return '2';
            case Keyboard.KEY_3: return '3';
            case Keyboard.KEY_4: return '4';
            case Keyboard.KEY_5: return '5';
            case Keyboard.KEY_6: return '6';
            case Keyboard.KEY_7: return '7';
            case Keyboard.KEY_8: return '8';
            case Keyboard.KEY_9: return '9';
            case Keyboard.KEY_0: return '0';
            case Keyboard.KEY_SPACE: return ' ';
            case Keyboard.KEY_COMMA: return ',';
            case Keyboard.KEY_PERIOD: return '.';
            case 12: return '-';
        }
        return INVALID_CHAR;
    }
}
