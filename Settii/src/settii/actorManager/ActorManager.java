package settii.actorManager;

import java.util.HashMap;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorManager {
    private ActorFactory factory;
    
    /**
     * Stores all created actortypes, i.e. when an actor is created from an XML-file, it is saved in the hashmap as a "prototype" for fast access
     */
    private HashMap<String, GameActor> actors;
    
    public ActorManager() {
        factory = new ActorFactory();
        actors = new HashMap<String, GameActor>();
    }
    
    public boolean init() {
        return true;
    }
    
    public void addActor(String resource, GameActor actor) {
        actors.put(resource, actor);
    }
    
    public GameActor createActor(String resource) {
        // if not yet created, create a new prototype
        if(!actors.containsKey(resource)) {
            GameActor actor = factory.createActor(resource);
            actors.put(resource, actor);
        }
        
        GameActor actor = actors.get(resource);
        GameActor returned = factory.createActor(resource);
        
        actor.copyTo(returned);
        
        return returned;
    }
    
}
