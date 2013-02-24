package settii.logic;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import settii.actorManager.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import settii.Application;
import settii.views.*;
import settii.actorManager.components.*;
import settii.eventManager.events.*;
import settii.logic.listeners.*;
import settii.physics.Physics;
/**
 *
 * @author Merioksan Mikko
 */
public class GameLogic implements IGameLogic {
    public enum GameState {
        MAIN_MENU, PLAYING, QUITTING
    }
 
    public GameState currentState;
    
    private ActorManager actorManager;
    private HashMap<Long, GameActor> actors;
    private SettiLogic game;
    private Physics physics;
    
    public GameLogic() {
        actorManager = new ActorManager();
        actors = new HashMap<Long, GameActor>();
        game = null;
        physics = null;
        
        changeState(GameState.MAIN_MENU);
        
        Application.get().getEventManager().register(ActorDestroyedEvent.eventType, new ActorDestroyedListener(this));
        Application.get().getEventManager().register(GameStateChangeEvent.eventType, new GameStateChangeListener(this));
    }
    
    public boolean init() {
        if(!actorManager.init()) {
            System.out.println("ActorManager init failed!");
            return false;
        }
        
        // TODO replace this with proper initialization of levels or somesuch stuff.
        game = new SettiLogic(this);
        
        physics = new Physics(this);
        if(!physics.init()) {
            return false;
        }
        
        return true;
    }
    
    public void changeState(GameState newState) {
        switch(newState) {
            case MAIN_MENU:
                clear();
                break;
            case PLAYING:
                break;
            case QUITTING:
                Application.get().quit();
                break;
        }
    }
    
    public void loadGame(String resource) {
        game = new SettiLogic(resource);
    }
    
    public SettiLogic getGame() {
        return game;
    }
    
    public ActorManager getActorManager() {
        return actorManager;
    }
    
    public void update(long deltaMs) {
        for(GameActor a : actors.values()) {
            a.update(deltaMs);
        }
        
        if(game != null) {
            game.update(deltaMs);
        }
        
        if(physics != null) {
            physics.update(deltaMs);
        }
    }
    
    public long createActor(String resource) {
        GameActor actor = actorManager.createActor(resource);
        actors.put(actor.getID(), actor);
        return actor.getID();
    }
    public void deleteActor(long id) {
        actors.remove(id);
    }
    public GameActor getActor(long id) {
        return actors.get(id);
    }
    public GameActor getActorAtLoc(double x, double y) {
        Iterator<GameActor> it = actors.values().iterator();
        
        while(it.hasNext()) {
            GameActor actor = it.next();
            if(actor.locationWithinActor(x, y)) {
                return actor;
            }
        }
        
        return null;
    }
    public Collection<GameActor> getActors() {
        return actors.values();
    }
    public void clear() {
        GameActor[] toClear = actors.values().toArray(new GameActor[actors.size()]);
        
        for(GameActor a : toClear) {
            Application.get().getEventManager().queueEvent(new ActorDestroyedEvent(a));
        }
    }
    
    @Override
    public void actorDestroyedListener(GameActor actor) {
        actors.remove(actor.getID());
    }
    
    public void gameStateChangeListener(GameState state) {
        changeState(state);
    }
}
