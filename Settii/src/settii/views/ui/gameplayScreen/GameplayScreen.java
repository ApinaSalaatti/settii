package settii.views.ui.gameplayScreen;

import java.util.Iterator;
import settii.views.ui.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import settii.actorManager.GameActor;
import settii.Application;
/**
 *
 * @author Merioksan Mikko
 */
public class GameplayScreen extends BaseGameScreen {
    public GameplayScreen() {
        
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        return true;
    }
    
    @Override
    public boolean onMouseUp(int mX, int mY, int button) {
        return true;
    }
    
    @Override
    public boolean onKeyDown(int key) {
        return true;
    }
    
    @Override
    public boolean onKeyUp(int key) {
        return true;
    }
    
    @Override
    public boolean onPointerMove(int mDX, int mDY) {
        return true;
    }
}
