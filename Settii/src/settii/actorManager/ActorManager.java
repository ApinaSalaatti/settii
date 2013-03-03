package settii.actorManager;

import settii.eventManager.events.researchEvents.UpdateDamageEvent;
import settii.Application;
import settii.actorManager.components.*;
import settii.actorManager.listeners.*;
import settii.eventManager.events.*;
import settii.eventManager.events.shopEvents.*;
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
        
        Application.get().getEventManager().register(UpdateDamageEvent.eventType, new UpdateDamageListener(this));
    }
    
    public boolean init() {
        return true;
    }
    
    public void addActor(String resource, GameActor actor) {
        actors.put(resource, actor);
    }
    
    public GameActor getPrototype(String resource) {
        return actors.get(resource);
    }
    public void createPrototype(String resource) {
        GameActor actor = factory.createActor(resource);
        actors.put(resource, actor);
    }
    
    public GameActor createActor(String resource) {
        // if not yet created, create a new prototype
        if(!actors.containsKey(resource)) {
            createPrototype(resource);
        }
        
        GameActor actor = actors.get(resource);
        GameActor returned = factory.createActor(resource);
        
        actor.copyTo(returned);
        
        return returned;
    }
    
    public void updateDamageListener(String resource, int damageToAdd) {
        GameActor actor = actors.get(resource);
        if(actor == null) {
            createPrototype(resource);
            actor = actors.get(resource);
        }
        
        WeaponsComponent wc = (WeaponsComponent)actor.getComponent("WeaponsComponent");
        wc.setDamage(wc.getDamage() + damageToAdd);
    }
}
