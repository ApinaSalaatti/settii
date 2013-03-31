package settii.actorManager;

import settii.eventManager.events.researchEvents.UpdateDamageEvent;
import settii.Application;
import settii.actorManager.components.*;
import settii.actorManager.listeners.*;
import java.util.HashMap;
import java.util.Iterator;
import settii.eventManager.events.researchEvents.UpdateRangeEvent;
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
        Application.get().getEventManager().register(UpdateRangeEvent.eventType, new UpdateRangeListener(this));
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
    
    public void clear() {
        actors.clear();
    }
    
    public void enableComponent(String comp) {
        Iterator<GameActor> it = actors.values().iterator();
        
        while(it.hasNext()) {
            GameActor actor = it.next();
            actor.enableComponent(comp);
        }
    }
    public void disableComponent(String comp) {
        Iterator<GameActor> it = actors.values().iterator();
        
        while(it.hasNext()) {
            GameActor actor = it.next();
            actor.disableComponent(comp);
        }
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
    
    public void updateRangeListener(String resource, int rangeToAdd) {
        GameActor actor = actors.get(resource);
        if(actor == null) {
            createPrototype(resource);
            actor = actors.get(resource);
        }
        
        WeaponsComponent wc = (WeaponsComponent)actor.getComponent("WeaponsComponent");
        wc.setRange(wc.getRange() + rangeToAdd);
    }
}
