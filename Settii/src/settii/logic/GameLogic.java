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
        UNINITIALIZED, INITIALIZED, MAIN_MENU, PLAYING, PAUSED, GAME_LOST, QUITTING
    }
 
    public GameState currentState;
    
    private ActorManager actorManager;
    private HashMap<Long, GameActor> actors;
    private ArrayList<Long> toDelete;
    private ArrayList<GameActor> created;
    private SettiLogic game;
    private Physics physics;
    
    public GameLogic() {
        actorManager = new ActorManager();
        actors = new HashMap<Long, GameActor>();
        toDelete = new ArrayList<Long>();
        created = new ArrayList<GameActor>();
        game = null;
        physics = null;
        
        //changeState(GameState.UNINITIALIZED);
        currentState = GameState.UNINITIALIZED;
        
        Application.get().getEventManager().register(ActorDestroyedEvent.eventType, new ActorDestroyedListener(this));
        Application.get().getEventManager().register(GameStateChangeEvent.eventType, new GameStateChangeListener(this));
    }
    
    public boolean init() {
        if(!actorManager.init()) {
            System.out.println("ActorManager init failed!");
            return false;
        }
        
        // TODO replace this with proper initialization of levels or somesuch stuff.
        //game = new SettiLogic(this);
        
        physics = new Physics(this);
        if(!physics.init()) {
            return false;
        }
        
        game = new SettiLogic(this);
        
        currentState = GameState.INITIALIZED;
        
        return true;
    }
    
    public void changeState(GameState newState) {
        switch(newState) {
            case INITIALIZED:
                currentState = GameState.INITIALIZED;
                break;
            case MAIN_MENU:
                clear();
                actorManager.clear();
                currentState = GameState.MAIN_MENU;
                break;
            case PLAYING:
                if(currentState != GameState.PAUSED) {
                    game.start();
                }
                currentState = GameState.PLAYING;
                break;
            case PAUSED:
                currentState = GameState.PAUSED;
                break;
            case GAME_LOST:
                currentState = GameState.GAME_LOST;
                break;
            case QUITTING:
                Application.get().quit();
                currentState = GameState.QUITTING;
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
        //System.out.println(currentState);
        // delete actors that were destroyd after the last update via events
        for(long a : toDelete) {
            deleteActor(a);
        }
        toDelete.clear();
        for(GameActor a : created) {
            actors.put(a.getID(), a);
        }
        created.clear();
        
        // game unitialized, an error has occured!
        if(currentState == GameState.UNINITIALIZED) {
            System.out.println("Game unitiliazied! Quitting.");
            Application.get().quit();
        }
        if(currentState == GameState.INITIALIZED) {
            // if we have only just initialized the game, go to main menu
            Application.get().getEventManager().queueEvent(new GameStateChangeEvent(GameState.MAIN_MENU));
        }
        if(currentState == GameState.PLAYING) {
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
    }
    
    public GameActor createActor(String resource) {
        GameActor actor = actorManager.createActor(resource);
        created.add(actor);
        //actors.put(actor.getID(), actor);
        return actor;
    }
    public void deleteActor(long id) {
        if(actors.get(id) != null) {
            actors.get(id).destroy();
            actors.remove(id);
        }
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
    public void enableComponent(String comp) {
        Iterator<GameActor> it = actors.values().iterator();
        
        while(it.hasNext()) {
            GameActor actor = it.next();
            actor.enableComponent(comp);
        }
        actorManager.enableComponent(comp);
    }
    public void disableComponent(String comp) {
        Iterator<GameActor> it = actors.values().iterator();
        
        while(it.hasNext()) {
            GameActor actor = it.next();
            actor.disableComponent(comp);
        }
        actorManager.disableComponent(comp);
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
        toDelete.add(actor.getID());
        //deleteActor(actor.getID());
    }
    
    public void gameStateChangeListener(GameState state) {
        changeState(state);
    }
}
