package settii.logic;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import settii.actorManager.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import settii.Application;
import settii.views.*;
import settii.actorManager.components.*;
/**
 *
 * @author Merioksan Mikko
 */
public class GameLogic {
    private ActorManager actorManager;
    private ActorFactory actorFactory;
    private HashMap<Long, GameActor> actors;
    private LevelLogic currentLevel;
    
    public GameLogic() {
        actorManager = new ActorManager();
        actorFactory = new ActorFactory();
        actors = new HashMap<Long, GameActor>();
        currentLevel = null;
    }
    
    public boolean init() {
        if(!actorManager.init()) {
            return false;
        }
        
        if(!actorFactory.init()) {
            return false;
        }
        
        // TODO replace this with proper initialization of levels or somesuch stuff.
        currentLevel = new LevelLogic();
        
        return true;
    }
    
    public void loadGame(String resource) {
        currentLevel = new LevelLogic(resource);
    }
    
    public LevelLogic getCurrentLevel() {
        return currentLevel;
    }
    
    public void update(long deltaMs) {
        if(currentLevel != null) {
            currentLevel.update(deltaMs);
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
}
