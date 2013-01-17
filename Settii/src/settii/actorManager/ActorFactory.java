package settii.actorManager;

/**
 *
 * @author Merioksan Mikko
 */
public class ActorFactory {
    public ActorFactory() {
        
    }
    
    public boolean init() {
        return true;
    }
    
    public GameActor createActor(String resource) {
        GameActor actor = new GameActor();
        
        return actor;
    }
}
