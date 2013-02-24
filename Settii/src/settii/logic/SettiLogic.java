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
import settii.logic.mouse.*;
import settii.logic.shop.*;
import java.util.Random;

import org.lwjgl.opengl.Display;
import settii.utils.MathUtil;
/**
 *
 * @author Merioksan Mikko
 */
public class SettiLogic implements IGameLogic {
    private GameLogic mainLogic;
    
    private IMouseAction currentMouseAction;
    
    ArrayList<Long> playerWeapons;
    ArrayList<Long> selectedWeapons;
    
    ArrayList<Long> buildings;
    
    private long time;
    private int sinceLastSpawn;
    
    private Player player;
    private Shop shop;
    
    public SettiLogic(GameLogic logic) {
        mainLogic = logic;
        
        currentMouseAction = new DefaultAction();
        
        playerWeapons = new ArrayList<Long>();
        selectedWeapons = new ArrayList<Long>();
        
        buildings = new ArrayList<Long>();
        
        time = 0;
        
        player = new Player();
        shop = new Shop();
        
        // register to listen input events
        Application.get().getEventManager().register(KeyDownEvent.eventType, new KeyDownListener(this));
        Application.get().getEventManager().register(KeyUpEvent.eventType, new KeyUpListener(this));
        Application.get().getEventManager().register(MouseDownEvent.eventType, new MouseDownListener(this));
        Application.get().getEventManager().register(MouseUpEvent.eventType, new MouseUpListener(this));
        Application.get().getEventManager().register(PointerMoveEvent.eventType, new PointerMoveListener(this));
        Application.get().getEventManager().register(ActorDestroyedEvent.eventType, new ActorDestroyedListener(this));
        Application.get().getEventManager().register(FireWeaponEvent.eventType, new FireWeaponListener(this));
        
        // cool testing stuff
        long id2 = mainLogic.createActor("assets/data/actors/cannon.xml");
        GameActor act2 = mainLogic.getActor(id2);
        act2.move(Display.getWidth() / 2, Display.getHeight() - 150);
        addPlayerWeapon(id2);
        
        for(int i = 0; i < 4; i++) {
            long b = mainLogic.createActor("assets/data/actors/building.xml");
            GameActor building = mainLogic.getActor(b);
            building.move(i*200 + 150, Display.getHeight() - 128 / 2);
            buildings.add(b);
        }
    }
    
    public SettiLogic(String resource) {
        
    }
    
    public void update(long deltaMs) {
        time += deltaMs;
        
        sinceLastSpawn += deltaMs;
        
        if(sinceLastSpawn > 5000) {
            int r = Application.get().getRNG().nextInt(6) + 1;
            if(r > 3) {
                r = Application.get().getRNG().nextInt(Display.getWidth() - 100) + 50;
                long id = mainLogic.createActor("assets/data/actors/enemy.xml");
                GameActor a = mainLogic.getActor(id);
                PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
                pc.setLocation(r, -50);
                pc.setAngleRad(MathUtil.ANGLE_STRAIGHT_DOWN);
                sinceLastSpawn = 0;
            }
        }
        
        player.update(deltaMs);
        
        currentMouseAction.update(deltaMs);
    }
    
    public ArrayList<Long> getPlayerWeapons() {
        return playerWeapons;
    }
    public ArrayList<Long> getSelectedWeapons() {
        return selectedWeapons;
    }
    public ArrayList<Long> getBuildings() {
        return buildings;
    }
    
    public Player getPlayer() {
        return player;
    }
    public Shop getShop() {
        return shop;
    }
    
    public IMouseAction getMouseAction() {
        return currentMouseAction;
    }
    
    public boolean KeyDownListener(int key) {
        return false;
    }
    
    public boolean KeyUpListener(int key) {
        return false;
    }
    
    public boolean MouseDownListener(int mX, int mY, int button) {
        currentMouseAction.execute(mX, mY, button);
        return true;
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
    
    public void addPlayerWeapon(long id) {
        playerWeapons.add(id);
    }
    public void removePlayerWeapon(long id) {
        playerWeapons.remove(id);
    }
    
    public void clearSelectedWeapons() {
        Long[] selected = selectedWeapons.toArray(new Long[selectedWeapons.size()]);
        for(long a : selected) {
            selectActor(a, false);
        }
    }
    
    public void setCurrentMouseAction(IMouseAction a) {
        currentMouseAction = a;
    }
    
    @Override
    public void actorDestroyedListener(GameActor actor) {
        player.actorDestroyedListener(actor);
        
        // just try to remove the actor from EVERYWHERE!
        selectedWeapons.remove(actor.getID());
        playerWeapons.remove(actor.getID());
        buildings.remove(actor.getID());
    }
    
    public void fireWeaponListener(long id) {
        GameActor actor = mainLogic.getActor(id);
        if(actor != null) {
            WeaponsComponent wc = (WeaponsComponent)actor.getComponent("WeaponsComponent");
            if(wc != null) {
                wc.fire();
            }
        }
    }
}
