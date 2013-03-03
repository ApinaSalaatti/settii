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
import settii.logic.research.*;
import java.util.Random;
import java.util.HashMap;
import java.util.Collection;
import settii.logic.sectors.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.input.Keyboard;
import settii.utils.MathUtil;
/**
 *
 * @author Merioksan Mikko
 */
public class SettiLogic implements IGameLogic {
    private GameLogic mainLogic;
    
    private IMouseAction currentMouseAction;
    
    private ArrayList<Long> playerWeapons;
    private ArrayList<Long> selectedWeapons;
    
    private ArrayList<Long> buildings;
    
    private HashMap<Long, GameSector> sectors;
    private GameSector currentSector;
    
    private long time;
    private int sinceLastSpawn;
    
    private Player player;
    private Shop shop;
    private Research research;
    private long baseID;
    
    public SettiLogic(GameLogic logic) {
        mainLogic = logic;
        
        currentMouseAction = new DefaultAction();
        
        playerWeapons = new ArrayList<Long>();
        selectedWeapons = new ArrayList<Long>();
        
        buildings = new ArrayList<Long>();
        
        time = 0;
        
        player = new Player();
        shop = new Shop();
        research = new Research();
        research.createFromXML(Application.get().getResourceManager().getDataManager().getData("assets/data/research.xml"));
        
        // register to listen input events
        Application.get().getEventManager().register(KeyDownEvent.eventType, new KeyDownListener(this));
        Application.get().getEventManager().register(KeyUpEvent.eventType, new KeyUpListener(this));
        Application.get().getEventManager().register(MouseDownEvent.eventType, new MouseDownListener(this));
        Application.get().getEventManager().register(MouseUpEvent.eventType, new MouseUpListener(this));
        Application.get().getEventManager().register(PointerMoveEvent.eventType, new PointerMoveListener(this));
        Application.get().getEventManager().register(ActorDestroyedEvent.eventType, new ActorDestroyedListener(this));
        Application.get().getEventManager().register(FireWeaponEvent.eventType, new FireWeaponListener(this));
        Application.get().getEventManager().register(ChangeSectorEvent.eventType, new ChangeSectorListener(this));
        
        // cool testing stuff
        sectors = new HashMap<Long, GameSector>();
        sectors.put(1L, new NorthSector(1L));
        sectors.put(2L, new EastSector(2L));
        sectors.put(3L, new SouthSector(3L));
        sectors.put(4L, new WestSector(4L));
        Application.get().getEventManager().queueEvent(new ChangeSectorEvent(2L));
        
        baseID = mainLogic.createActor("assets/data/actors/base.xml");
        mainLogic.getActor(baseID).move(Display.getWidth() * 1.5f, Display.getHeight() * 1.5f);
        
        /*
        long id2 = mainLogic.createActor("assets/data/actors/cannon.xml");
        GameActor act2 = mainLogic.getActor(id2);
        act2.move(Display.getWidth() * 2 + 50, Display.getHeight() + 50);
        addPlayerWeapon(id2);
        
        for(int i = 0; i < 4; i++) {
            long b = mainLogic.createActor("assets/data/actors/building.xml");
            GameActor building = mainLogic.getActor(b);
            building.move(i*200 + Display.getWidth() * 2, Display.getHeight() + 200);
            buildings.add(b);
        }
        * 
        */
    }
    
    public SettiLogic(String resource) {
        
    }
    
    public void update(long deltaMs) {
        time += deltaMs;
        
        sinceLastSpawn += deltaMs;
        
        if(sinceLastSpawn > 5000) {
            int r = Application.get().getRNG().nextInt(6) + 1;
            sectors.get(2L).spawnEnemy();
            /*
            if(r > 3) {
                r = Application.get().getRNG().nextInt(Display.getWidth() - 100) + 50;
                long id = mainLogic.createActor("assets/data/actors/enemy.xml");
                GameActor a = mainLogic.getActor(id);
                PhysicsComponent pc = (PhysicsComponent)a.getComponent("PhysicsComponent");
                pc.setLocation(r, -50);
                pc.setAngleRad(MathUtil.ANGLE_STRAIGHT_DOWN);
                sinceLastSpawn = 0;
            }
            * 
            */
            sinceLastSpawn = 0;
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
    public long getBaseID() {
        return baseID;
    }
    
    public Player getPlayer() {
        return player;
    }
    public Shop getShop() {
        return shop;
    }
    public Research getResearch() {
        return research;
    }
    
    public GameSector getSector(long id) {
        return sectors.get(id);
    }
    public GameSector getCurrentSector() {
        return currentSector;
    }
    public Collection<GameSector> getSectors() {
        return sectors.values();
    }
    
    public IMouseAction getMouseAction() {
        return currentMouseAction;
    }
    
    public boolean KeyDownListener(int key) {
        if(key == Keyboard.KEY_LEFT) {
            Application.get().getEventManager().queueEvent(new ChangeSectorEvent(4L));
            return true;
        }
        if(key == Keyboard.KEY_RIGHT) {
            Application.get().getEventManager().queueEvent(new ChangeSectorEvent(2L));
            return true;
        }
        if(key == Keyboard.KEY_UP) {
            Application.get().getEventManager().queueEvent(new ChangeSectorEvent(1L));
            return true;
        }
        if(key == Keyboard.KEY_DOWN) {
            Application.get().getEventManager().queueEvent(new ChangeSectorEvent(3L));
            return true;
        }
        return false;
    }
    
    public boolean KeyUpListener(int key) {
        return false;
    }
    
    public boolean MouseDownListener(int mX, int mY, int button) {
        currentMouseAction.onMouseDown(mX, mY, button);
        return true;
    }
    
    public boolean MouseUpListener(int mX, int mY, int button) {
        currentMouseAction.onMouseUp(mX, mY, button);
        return false;
    }
    
    public boolean PointerMoveListener(int mX, int mY, int mDX, int mDY) {
        currentMouseAction.onPointerMove(mX, mY, mDX, mDY);
        
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
    
    public void changeSectorListener(long id) {
        currentSector = sectors.get(id);
    }
}
