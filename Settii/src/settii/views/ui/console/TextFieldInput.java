package settii.views.ui.console;

import org.lwjgl.input.Keyboard;
import settii.Application;
import settii.views.humanView.renderer.Renderer;
import settii.views.ui.BaseScreenItem;
/**
 *
 * @author Merioksan Mikko
 */
public class TextFieldInput extends BaseScreenItem {
    public static char INVALID_CHAR = (char)-1;
    private String currentInput;
    
    public TextFieldInput() {
        super(10, 532, 512, 64, "assets/graphics/ui/console/textFieldInput.png");
        currentInput = "";
        selected = true;
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        super.onMouseDown(mX, mY, button);
        
        if(mX > x && mX < x + width && mY > y && mY < y + height) {
            selected = true;
            return true;
        }
        else {
            selected = false;
        }
        
        return false;
    }
    
    @Override
    public boolean onKeyDown(int key) {
        super.onKeyDown(key);
        
        if(selected) {
            if(key == Keyboard.KEY_RETURN) {
                ConsoleFactory.get().processInput(currentInput);
                currentInput = "";
                return true;
            }
            else if(key == Keyboard.KEY_BACK && currentInput.length() > 0) {
                currentInput = currentInput.substring(0, currentInput.length()-1);
                return true;
            }
            else if(getChar(key) != INVALID_CHAR) {
                currentInput += getChar(key);
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void render() {
        super.render();
        Renderer.get().drawText(currentInput, x+16, y+16);
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
