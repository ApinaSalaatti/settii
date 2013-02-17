package settii.actorManager;

import java.util.HashMap;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorManager {
    /**
     * Stores all created actortypes, i.e. when an actor is created from an XML-file, it is saved in the hashmap as a "prototype" for fast access
     */
    private HashMap<String, GameActor> actors;
    
    public ActorManager() {
        actors = new HashMap<String, GameActor>();
    }
    
    public boolean init() {
        return true;
    }
    
    public void addActor(String resource, GameActor actor) {
        actors.put(resource, actor);
    }
    
}
