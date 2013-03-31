package settii.logic;

import java.util.ArrayList;
import settii.Application;
import settii.actorManager.components.*;
import settii.actorManager.GameActor;
import settii.eventManager.events.*;
import settii.logic.listeners.*;
import settii.logic.mouse.*;
import settii.logic.shop.*;
import settii.logic.research.*;
import java.util.HashMap;
import java.util.Collection;
import settii.logic.sectors.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.input.Keyboard;
import settii.eventManager.events.shopEvents.RepairBaseEvent;
import settii.logic.messaging.Message;
import settii.logic.messaging.Messaging;
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
    private ArrayList<WeaponLocation> weaponLocations;
    
    private long time;
    
    private Player player;
    private Shop shop;
    private Research research;
    private long baseID;
    private boolean playerLost;
    
    private EnemyManager enemyManager;
    
    private Messaging messaging;
    
    public SettiLogic(GameLogic logic) {
        mainLogic = logic;
        
        playerWeapons = new ArrayList<Long>();
        selectedWeapons = new ArrayList<Long>();
        
        buildings = new ArrayList<Long>();
        
        weaponLocations = new ArrayList<WeaponLocation>();
        
        // register to listen events
        Application.get().getEventManager().register(KeyDownEvent.eventType, new KeyDownListener(this));
        Application.get().getEventManager().register(KeyUpEvent.eventType, new KeyUpListener(this));
        Application.get().getEventManager().register(MouseDownEvent.eventType, new MouseDownListener(this));
        Application.get().getEventManager().register(MouseUpEvent.eventType, new MouseUpListener(this));
        Application.get().getEventManager().register(PointerMoveEvent.eventType, new PointerMoveListener(this));
        Application.get().getEventManager().register(ActorDestroyedEvent.eventType, new ActorDestroyedListener(this));
        Application.get().getEventManager().register(FireWeaponEvent.eventType, new FireWeaponListener(this));
        Application.get().getEventManager().register(ChangeSectorEvent.eventType, new ChangeSectorListener(this));
        Application.get().getEventManager().register(RepairBaseEvent.eventType, new RepairBaseListener(this));
        Application.get().getEventManager().register(WeaponFiredEvent.eventType, new WeaponFiredListener(this));
        
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
        
        player = new Player();
        shop = new Shop();
        research = new Research();
        messaging = new Messaging();
        enemyManager = new EnemyManager(this);
    }
    
    public SettiLogic(String resource) {
        
    }
    
    public void start() {
        currentMouseAction = new DefaultAction();
        
        playerWeapons.clear();
        selectedWeapons.clear();
        
        buildings.clear();
        
        time = 0;
        
        player.createFromXML(null);
        shop.createFromXML(Application.get().getResourceManager().getDataManager().getData("assets/data/shop.xml"));
        research.createFromXML(Application.get().getResourceManager().getDataManager().getData("assets/data/research.xml"));
        
        // cool testing stuff
        sectors = new HashMap<Long, GameSector>();
        sectors.put(1L, new NorthSector(1L));
        sectors.put(2L, new EastSector(2L));
        sectors.put(3L, new SouthSector(3L));
        sectors.put(4L, new WestSector(4L));
        Application.get().getEventManager().queueEvent(new ChangeSectorEvent(1L));
        
        weaponLocations.clear();
        weaponLocations.add(new WeaponLocation(400, 500));
        
        GameActor a = mainLogic.createActor("assets/data/actors/base.xml");
        baseID = a.getID();
        a.move(Display.getWidth() * 1.5f, Display.getHeight() * 1.5f);
        playerLost = false;
        
        messaging.clear();
        
        enemyManager.start();
    }
    
    public void update(long deltaMs) {
        if(mainLogic.currentState == GameLogic.GameState.PLAYING) {
            time += deltaMs;
            
            for(WeaponLocation wl : weaponLocations) {
                wl.update(deltaMs);
            }
        }
        
        if(playerLost) {
            Application.get().getEventManager().queueEvent(new GameStateChangeEvent(GameLogic.GameState.GAME_LOST));
        }
        
        //spawnEnemies(deltaMs);
        enemyManager.update(deltaMs);
        
        player.update(deltaMs);
        
        currentMouseAction.update(deltaMs);
        
        messaging.update(deltaMs);
    }
    
    public long getTime() {
        return time;
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
    
    public ArrayList<WeaponLocation> getWeaponLocations() {
        return weaponLocations;
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
    
    public Message getCurrentMessage() {
        return messaging.getCurrentMessage();
    }
    
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
    
    public boolean KeyDownListener(int key) {
        if(mainLogic.currentState == GameLogic.GameState.PLAYING) {
            /*
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
            * 
            */
        }
        return false;
    }
    
    public boolean KeyUpListener(int key) {
        return false;
    }
    
    public boolean MouseDownListener(int mX, int mY, int button) {
        if(mainLogic.currentState == GameLogic.GameState.PLAYING) {
            if(currentMouseAction.onMouseDown(mX, mY, button)) {
                return true;
            }

            for(long a : selectedWeapons) {
                GameActor act = mainLogic.getActor(a);
                PhysicsComponent pc = (PhysicsComponent)act.getComponent("PhysicsComponent");
                pc.setTarget(mX, mY);
            }

            return true;
        }
        
        return false;
    }
    
    public boolean MouseUpListener(int mX, int mY, int button) {
        if(mainLogic.currentState == GameLogic.GameState.PLAYING) {
            currentMouseAction.onMouseUp(mX, mY, button);
        }
        return false;
    }
    
    public boolean PointerMoveListener(int mX, int mY, int mDX, int mDY) {
        if(mainLogic.currentState == GameLogic.GameState.PLAYING) {
            currentMouseAction.onPointerMove(mX, mY, mDX, mDY);
        }
        
        /*
        for(long a : selectedWeapons) {
            GameActor act = mainLogic.getActor(a);
            PhysicsComponent pc = (PhysicsComponent)act.getComponent("PhysicsComponent");
            pc.setTarget(mX, mY);
        }
        * 
        */
        return false;
    }
    
    public void selectActor(long id, boolean select) {
        if(select) {
            selectedWeapons.add(id);
            //Application.get().getEventManager().queueEvent(new ActorTextureChangeEvent(id, "selected"));
        }
        else {
            selectedWeapons.remove(id);
            //Application.get().getEventManager().queueEvent(new ActorTextureChangeEvent(id, "default"));
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
        // just try to remove the actor from EVERYWHERE!
        selectedWeapons.remove(actor.getID());
        playerWeapons.remove(actor.getID());
        buildings.remove(actor.getID());
        
        if(actor.getID() == baseID) {
            playerLost = true;
        }
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
    
    public void repairBaseListener() {
        GameActor base = mainLogic.getActor(baseID);
        PhysicsComponent pc = (PhysicsComponent)base.getComponent("PhysicsComponent");
        pc.setHealth(pc.getMaxHealth());
    }
    
    public void weaponFiredListener(long actor) {
        /*
        GameActor a = mainLogic.getActor(actor);
        WeaponsComponent wc1 = (WeaponsComponent)a.getComponent("WeaponsComponent");
        PhysicsComponent pc1 = (PhysicsComponent)a.getComponent("PhysicsComponent");
        StatusComponent sc1 = (StatusComponent)a.getComponent("StatusComponent");

        String bullet = wc1.getBullet();
        int damage = wc1.getDamage();
        
        long id = Application.get().getLogic().createActor(bullet);
        GameActor bul = Application.get().getLogic().getActor(id);

        float weaponDist = pc1.getHeight() / 2 + 10; // barrel of the gun is in front of the weapon

        // calculate correct spot with angle of the actor
        float bulletY = pc1.getY() - weaponDist * (float)Math.sin(pc1.getAngleRad());
        float bulletX = pc1.getX() - weaponDist * (float)Math.cos(pc1.getAngleRad());
        bul.move(bulletX, bulletY);

        PhysicsComponent pc2 = (PhysicsComponent)bul.getComponent("PhysicsComponent");
        StatusComponent sc2 = (StatusComponent)bul.getComponent("StatusComponent");
        // the projectile must know who shot it to prevent friendly fire
        sc2.setAllegiance(sc1.getAllegiance());

        pc2.setDamage(damage);
        pc2.setAngleRad(pc1.getAngleRad());
        pc2.applyAcceleration(1.0f);
        * 
        */
    }
}
