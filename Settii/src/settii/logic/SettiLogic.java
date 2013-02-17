package settii.logic;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import org.lwjgl.input.Mouse;
import settii.Application;
import settii.actorManager.components.*;
import settii.actorManager.GameActor;
import settii.views.ui.IScreenItem;
import settii.eventManager.events.*;
import settii.eventManager.EventManager;
import settii.logic.listeners.*;
import java.util.Random;

import org.lwjgl.opengl.Display;
/**
 *
 * @author Merioksan Mikko
 */
public class SettiLogic extends GameLogic {
    private GameLogic mainLogic;
    
    ArrayList<Long> playerWeapons;
    ArrayList<Long> selectedWeapons;
    
    private long time;
    private int sinceLastSpawn;
    
    private Random rng;
    
    public SettiLogic(GameLogic logic) {
        mainLogic = logic;
        
        playerWeapons = new ArrayList<Long>();
        selectedWeapons = new ArrayList<Long>();
        
        time = 0;
        
        rng = new Random();
        
        // register to listen input events
        Application.get().getEventManager().register(KeyDownEvent.eventType, new KeyDownListener(this));
        Application.get().getEventManager().register(KeyUpEvent.eventType, new KeyUpListener(this));
        Application.get().getEventManager().register(MouseDownEvent.eventType, new MouseDownListener(this));
        Application.get().getEventManager().register(MouseUpEvent.eventType, new MouseUpListener(this));
        Application.get().getEventManager().register(PointerMoveEvent.eventType, new PointerMoveListener(this));
        Application.get().getEventManager().register(ActorDestroyedEvent.eventType, new ActorDestroyedListener(this));
        
        // cool testing stuff
        long id2 = mainLogic.createActor("assets/data/actors/cannon.xml");
        GameActor act2 = mainLogic.getActor(id2);
        act2.move(Display.getWidth() / 2, Display.getHeight() - 50);
        
        long id1 = mainLogic.createActor("assets/data/actors/enemy.xml");
        GameActor act1 = mainLogic.getActor(id1);
        act1.move(Display.getWidth() / 2, 50);
        
    }
    
    public SettiLogic(String resource) {
        
    }
    
    @Override
    public void update(long deltaMs) {
        time += deltaMs;
        
        sinceLastSpawn += deltaMs;
        
        if(sinceLastSpawn > 5000) {
            int r = rng.nextInt(6) + 1;
            if(r > 3) {
                r = rng.nextInt(Display.getWidth() - 100) + 50;
                long id = mainLogic.createActor("assets/data/actors/enemy.xml");
                GameActor a = mainLogic.getActor(id);
                a.move(r, 50);
                sinceLastSpawn = 0;
            }
        }
    }
    
    public ArrayList<Long> getPlayerWeapons() {
        return playerWeapons;
    }
    public ArrayList<Long> getSelectedWeapons() {
        return selectedWeapons;
    }
    
    public boolean KeyDownListener(int key) {
        return false;
    }
    
    public boolean KeyUpListener(int key) {
        return false;
    }
    
    public boolean MouseDownListener(int mX, int mY, int button) {
        if(button == 0) {
            if(Application.get().getLogic().getActorAtLoc(mX, mY) != null) {
                long id = Application.get().getLogic().getActorAtLoc(mX, mY).getID();
                if(selectedWeapons.contains(id)) {
                    selectActor(id, false);
                }
                else {
                    selectActor(id, true);
                }
            }
            else {
                for(long a : selectedWeapons) {
                    GameActor actor = mainLogic.getActor(a);
                    WeaponsComponent wc = (WeaponsComponent)actor.getComponent("WeaponsComponent");
                    if(wc != null) {
                        wc.fire();
                        Application.get().getEventManager().queueEvent(new FireWeaponEvent(a));
                    }
                }
            }
        }
        else if(button == 1) {
            Long[] deselected = selectedWeapons.toArray(new Long[selectedWeapons.size()]);
            for(long a : deselected) {
                selectActor(a, false);
            }
        }
        return false;
    }
    
    public boolean MouseUpListener(int mX, int mY, int button) {
        return false;
    }
    
    public boolean PointerMoveListener(int mX, int mY, int mDX, int mDY) {
        for(long a : selectedWeapons) {
            GameActor act = mainLogic.getActor(a);
            PhysicsComponent pc = (PhysicsComponent)act.getComponent("PhysicsComponent");
            pc.setTarget(mX, mY);
        }
        return false;
    }
    
    public void selectActor(long id, boolean select) {
        if(select) {
            selectedWeapons.add(id);
            Application.get().getEventManager().queueEvent(new ActorTextureChangeEvent(id, "selected"));
        }
        else {
            selectedWeapons.remove(id);
            Application.get().getEventManager().queueEvent(new ActorTextureChangeEvent(id, "default"));
        }
        Application.get().getEventManager().queueEvent(new ActorSelectedEvent(id, select));
    }
    
    @Override
    public void actorDestroyedListener(long id) {
        selectedWeapons.remove(id);
        playerWeapons.remove(id);
    }
}
