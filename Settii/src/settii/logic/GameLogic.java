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
public class GameLogic {
    private ActorManager actorManager;
    private ActorFactory actorFactory;
    private HashMap<Long, GameActor> actors;
    private SettiLogic game;
    private Physics physics;
    
    public GameLogic() {
        actorManager = new ActorManager();
        actorFactory = new ActorFactory();
        actors = new HashMap<Long, GameActor>();
        game = null;
        physics = null;
        
        Application.get().getEventManager().register(ActorDestroyedEvent.eventType, new ActorDestroyedListener(this));
    }
    
    public boolean init() {
        if(!actorManager.init()) {
            return false;
        }
        
        if(!actorFactory.init()) {
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
    
    public void loadGame(String resource) {
        game = new SettiLogic(resource);
    }
    
    public SettiLogic getGame() {
        return game;
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
        GameActor actor = actorFactory.createActor(resource);
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
    
    public void actorDestroyedListener(long id) {
        actors.remove(id);
    }
}
