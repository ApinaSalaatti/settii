package settii.logic;

import settii.actorManager.*;
import java.util.HashMap;
import java.util.ArrayList;
import settii.views.*;
/**
 *
 * @author Merioksan Mikko
 */
public class GameLogic {
    private ActorManager actorManager;
    private ActorFactory actorFactory;
    private HashMap<Long, GameActor> actors;
    private ArrayList<IGameView> views;
    
    public GameLogic() {
        actorManager = new ActorManager();
        actorFactory = new ActorFactory();
        actors = new HashMap<Long, GameActor>();
        views = new ArrayList<IGameView>();
    }
    
    public boolean init() {
        if(!actorManager.init()) {
            return false;
        }
        
        if(!actorFactory.init()) {
            return false;
        }
        
        HumanView hv = new HumanView();
        if(!hv.init()) {
            return false;
        }
        addView(hv);
        
        return true;
    }
    
    public void addView(IGameView view) {
        views.add(view);
    }
    
    public void update(long deltaMs) {
        
    }
    
    public long createActor() {
        GameActor actor = actorFactory.createActor();
        actors.put(actor.getID(), actor);
        
        return actor.getID();
    }
    public void deleteActor(long id) {
        actors.remove(id);
    }
}
