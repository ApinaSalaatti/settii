package settii.views.humanView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import settii.views.ui.*;
import settii.Application;
import settii.eventManager.events.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import settii.logic.GameLogic;
import settii.utils.MathUtil;
import settii.utils.actions.PlayAnimationAction;
import settii.views.IGameView;
import settii.views.humanView.animation.AnimationData;
import settii.views.humanView.audioPlayer.AudioPlayer;
import settii.views.humanView.renderer.Renderer;
import settii.views.humanView.listeners.*;
import settii.views.ui.gameplayScreen.*;
import settii.views.ui.mainMenuScreen.*;
import settii.views.ui.commandCenterScreen.*;
import settii.views.humanView.renderer.Texture;
import settii.views.ui.highScoreScreen.HighScoreScreenFactory;
/**
 *
 * @author Merioksan Mikko
 */
public class HumanView implements IGameView {
    private Camera camera;
    
    // mouse stuff
    private Texture cursor;
    private float mouseX, mouseY;
    private float mSensitivity;
    
    private GameScene scene;
    private ArrayDeque<IGameScreen> screens;
    
    private ArrayList<PlayAnimationAction> animations;
    
    private AudioPlayer audioPlayer;
    //private IntBuffer currentMusic;
    
    private Tooltip tooltip; // this shit's here so I can render it last. There's prolly a better way?

    public HumanView() {
        scene = new GameScene();
        screens = new ArrayDeque<IGameScreen>();
        
        audioPlayer = new AudioPlayer();
        
        camera = new Camera();
        
        animations = new ArrayList<PlayAnimationAction>();
        
        //screens.addFirst(GameplayScreenFactory.create());
        
        Application.get().getEventManager().register(GameStateChangeEvent.eventType, new GameStateChangeListener(this));
        Application.get().getEventManager().register(ChangeSectorEvent.eventType, new ChangeSectorListener(this));
        
        tooltip = null;
    }
    
    public boolean init() {
        if(!scene.init()) {
            System.out.println("Error initializing the scene");
            return false;
        }
        
        cursor = Application.get().getResourceManager().getTextureManager().getTexture("assets/graphics/ui/cursor.png");
        mouseX = Display.getWidth() / 2;
        mouseY = Display.getHeight() / 2;
        
        return true;
    }

    public void setCursor(Texture tex) {
        cursor = tex;
    }
    public void setCursor(String res) {
        cursor = Application.get().getResourceManager().getTextureManager().getTexture(res);
    }
    
    public void setTooltip(Tooltip t) {
        tooltip = t;
    }
    
    public Camera getCamera() {
        return camera;
    }
    
    @Override
    public void update(long deltaMs) {
        getInput();

        camera.update(deltaMs);
        scene.update(deltaMs);
        
        // clear screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        // set the viewing location, inverted so stuff is drawn correctly in the opposite direction
        float x = -camera.getX();
        float y = -camera.getY();
        
        // render the actors
        Renderer.get().setOffset(x, y);
        Renderer.get().begin();
        scene.render();
        
        Iterator<PlayAnimationAction> i = animations.iterator();
        while(i.hasNext()) {
            PlayAnimationAction paa = i.next();
            paa.update(deltaMs);
            if(paa.finished()) {
                i.remove();
            }
            else {
                Renderer.get().draw(paa.getTexture(), paa.getX(), paa.getY(), paa.getFrameWidth(), paa.getFrameHeight(), paa.getFrameWidth() / 2, paa.getFrameHeight() / 2, MathUtil.degToRad(90), paa.getU(), paa.getV(), paa.getU2(), paa.getV2());
            }
        }
        /*
         * x = x - ro.getTexWidth() / 2;
        y = y - ro.getTexHeight() / 2;
        
        batch.draw(ro.getTexture(), x, y, ro.getWidth(), ro.getHeight(), ro.getTexWidth() / 2, ro.getTexHeight() / 2, MathUtil.toRenderAngle(ro.getRotation()), ro.getU(), ro.getV(), ro.getU2(), ro.getV2());
         * 
         */
        Renderer.get().end();
        
        // render and update user interface stuff
        // TODO should we update here?? Propably, so we don't have to iterate through the screens many times...
        // set offset uniform to zero for user interface rendering
        Renderer.get().setOffset(0, 0);
        Renderer.get().begin();
        Iterator<IGameScreen> it = screens.descendingIterator();
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            screen.update(deltaMs);
            screen.render();
        }
        
        // render cursor and tooltip last
        //Renderer.get().draw(cursor, mouseX-cursor.getWidth()/2, mouseY-cursor.getHeight()/2);
        if(tooltip != null) {
            tooltip.render();
        }
        
        Renderer.get().end();
        
        audioPlayer.update(deltaMs);
    }
    
    public void addScreen(IGameScreen screen) {
        screens.addFirst(screen);
    }
    public IGameScreen popScreen() {
        return screens.pollFirst();
    }
    public void clearScreens() {
        screens.clear();
    }
    
    public void playAnimation(Texture tex, AnimationData data, long length, float x, float y) {
        animations.add(new PlayAnimationAction(tex, data, length, x, y));
    }
    
    // keyboard input
    public boolean getInput() {
        while(Keyboard.next()) {
	    if(Keyboard.getEventKeyState()) {
                onKeyDown(Keyboard.getEventKey());
	    } else {
	        onKeyUp(Keyboard.getEventKey());
	    }
	}
        
        // mouse input
        //float mDX = Mouse.getDX() * 2.5f; // mouse sensitivity 2.5, testing stuff...
        //float mDY = -Mouse.getDY() * 2.5f; // invert y-axis to match all our coordinates
        
        //mouseX += mDX;
        //mouseY += mDY;
        
        float mDX = Mouse.getDX();
        float mDY = -Mouse.getDY();
        mouseX = Mouse.getX();
        mouseY = Display.getHeight() - Mouse.getY();
        /*
        if(mouseX < 0) {
            mouseX = 0;
        }
        else if(mouseX > Display.getWidth()) {
            mouseX = Display.getWidth();
        }
        if(mouseY < 0) {
            mouseY = 0;
        }
        else if(mouseY > Display.getHeight()) {
            mouseY = Display.getHeight();
        }
        * 
        */
        
        while(Mouse.next()) {
            int button = Mouse.getEventButton();
            int mX = Mouse.getEventX();
            int mY = Display.getHeight() - Mouse.getEventY(); // invert the y-axis to match all our coordinates
            if(button != -1) { // check that a state of a mouse button has changed
                if(Mouse.getEventButtonState()) {
                    onMouseDown((int)mX, (int)mY, button);
                }
                else {
                    onMouseUp((int)mX, (int)mY, button);
                }
            }
        }
        
        /*
        int mX = Mouse.getX();
        int mY = Display.getHeight() - Mouse.getY(); // invert the y-axis to match all our coordinates
        //mouseX = mX;
        //mouseY = mY;
        int mDX = Mouse.getDX();
        int mDY = -Mouse.getDY(); // invert y-axis yet again
        
        mouseX += mDX * 2.5f;
        mouseY += mDY * 2.5f;
        * 
        */
        
        if(mDX != 0 || mDY != 0) {
            onPointerMove((int)mouseX, (int)mouseY, (int)mDX, (int)mDY);
        }
        
        return false;
    }
    
    @Override
    public boolean onKeyDown(int key) {
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onKeyDown(key)) {
                return true;
            }
        }
        
        if(camera.onKeyDown(key)) {
            return true;
        }
        
        Application.get().getEventManager().queueEvent(new KeyDownEvent(key));
        
        return false;
    }
    
    @Override
    public boolean onKeyUp(int key) {
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onKeyUp(key)) {
                return true;
            }
        }
        
        if(camera.onKeyUp(key)) {
            return true;
        }
        
        Application.get().getEventManager().queueEvent(new KeyUpEvent(key));
        
        return false;
    }
    
    @Override
    public boolean onMouseDown(int mX, int mY, int button) {
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onMouseDown(mX, mY, button)) {
                return true;
            }
        }
        
        // to change mouse coordinates to "world" coordinates, we must take camera location to accord
        mX = (int)(mX + camera.getX());
        mY = (int)(mY + camera.getY());
        
        if(camera.onMouseDown(mX, mY, button)) {
            return true;
        }
        
        Application.get().getEventManager().queueEvent(new MouseDownEvent(mX, mY, button));
        
        return false;
    }
    
    @Override
    public boolean onMouseUp(int mX, int mY, int button) {
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onMouseUp(mX, mY, button)) {
                return true;
            }
        }
        
        if(camera.onMouseUp(mX, mY, button)) {
            return true;
        }
        
        // to change mouse coordinates to "world" coordinates, we must take camera location into accord
        mX = (int)(mX + camera.getX());
        mY = (int)(mY + camera.getY());
        
        Application.get().getEventManager().queueEvent(new MouseUpEvent(mX, mY, button));
        
        return false;
    }
    
    @Override
    public boolean onPointerMove(int mX, int mY, int mDX, int mDY) {
        
        Iterator<IGameScreen> it = screens.iterator();
        
        while(it.hasNext()) {
            IGameScreen screen = it.next();
            if(screen.onPointerMove(mX, mY, mDX, mDY)) {
                return true;
            }
        }
        
        if(camera.onPointerMove(mX, mY, mDX, mDY)) {
            return true;
        }
        
        // to change mouse coordinates to "world" coordinates, we must take camera location into accord
        mX = (int)(mX + camera.getX());
        mY = (int)(mY + camera.getY());
        
        Application.get().getEventManager().queueEvent(new PointerMoveEvent(mX, mY, mDX, mDY));
        
        return false;
    }
    
    public void changeSectorListener(long id) {
        float sX = Application.get().getLogic().getGame().getSector(id).getX();
        float sY = Application.get().getLogic().getGame().getSector(id).getY();
        
        camera.setLocation(sX, sY);
    }
    
    public void gameStateChangeListener(GameLogic.GameState state) {
        switch(state) {
            case PLAYING:
                //audioPlayer.stop(currentMusic);
                clearScreens();
                addScreen(GameplayScreenFactory.create());
                audioPlayer.playMusic("gameplay", true);
                break;
            case MAIN_MENU:
                //audioPlayer.stop(currentMusic);
                clearScreens();
                addScreen(MainMenuScreenFactory.create());
                audioPlayer.playMusic("main menu", true);
                break;
            case GAME_LOST:
                clearScreens();
                addScreen(HighScoreScreenFactory.create());
                break;
        }
    }
}
