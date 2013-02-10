package settii.logic;

import settii.logic.listeners.SelectedActorsClearedListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import settii.Application;
import settii.actorManager.GameActor;
import settii.views.ui.IScreenItem;
import settii.eventManager.events.*;
import settii.eventManager.EventManager;
import settii.logic.listeners.*;

import org.lwjgl.opengl.Display;
/**
 *
 * @author Merioksan Mikko
 */
public class SettiLogic {
    private GameLogic mainLogic;
    
    ArrayList<Long> playerWeapons;
    ArrayList<Long> selectedWeapons;
    
    public SettiLogic(GameLogic logic) {
        mainLogic = logic;
        
        playerWeapons = new ArrayList<Long>();
        selectedWeapons = new ArrayList<Long>();
        
        // register to listen input events
        Application.get().getEventManager().register(KeyDownEvent.eventType, new KeyDownListener(this));
        Application.get().getEventManager().register(KeyUpEvent.eventType, new KeyUpListener(this));
        Application.get().getEventManager().register(MouseDownEvent.eventType, new MouseDownListener(this));
        Application.get().getEventManager().register(MouseUpEvent.eventType, new MouseUpListener(this));
        Application.get().getEventManager().register(PointerMoveEvent.eventType, new PointerMoveListener(this));
        
        
        Application.get().getEventManager().register(ActorSelectedEvent.eventType, new ActorSelectedListener(this));
        Application.get().getEventManager().register(SelectedActorsClearedEvent.eventType, new SelectedActorsClearedListener(this));
        
        // cool testing stuff
        long id2 = Application.get().getLogic().createActor("assets/data/actors/cannon.xml");
        GameActor act2 = Application.get().getLogic().getActor(id2);
        act2.move(Display.getWidth() / 2, Display.getHeight() - 50);
    }
    
    public SettiLogic(String resource) {
        
    }
    
    public void update(long deltaMs) {
        
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
                    Application.get().getEventManager().queueEvent(new ActorSelectedEvent(id, false));
                }
                else {
                    Application.get().getEventManager().queueEvent(new ActorSelectedEvent(id, true));
                }
            }
            else {
                for(long a : selectedWeapons) {
                    Application.get().getEventManager().queueEvent(new FireWeaponEvent(a));
                }
            }
        }
        else if(button == 1) {
            Application.get().getEventManager().queueEvent(new SelectedActorsClearedEvent());
        }
        return false;
    }
    
    public boolean MouseUpListener(int mX, int mY, int button) {
        return false;
    }
    
    public boolean PointerMoveListener(int mDX, int mDY) {
        return false;
    }
    
    public void actorSelectedListener(ActorSelectedEvent ase) {
        if(ase.selected()) {
            selectedWeapons.add(ase.getActor());
            Application.get().getEventManager().queueEvent(new ActorTextureChangeEvent(ase.getActor(), "selected"));
        }
        else {
            selectedWeapons.remove(ase.getActor());
            Application.get().getEventManager().queueEvent(new ActorTextureChangeEvent(ase.getActor(), "default"));
        }
    }
    
    public void selectedActorsClearedListener() {
        for(long id : selectedWeapons) {
            Application.get().getEventManager().queueEvent(new ActorTextureChangeEvent(id, "default"));
        }
        selectedWeapons.clear();
    }
}
