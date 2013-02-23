package settii.actorManager;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import settii.Application;
import settii.actorManager.components.*;
import settii.eventManager.events.ActorCreatedEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorFactory {
    private long lastID;
    private ActorManager manager;
    
    public ActorFactory() {
        lastID = 0;
        manager = new ActorManager();
    }
    
    public boolean init() {
        if(!manager.init()) {
            System.out.println("ActorFactory initialization failed!");
            return false;
        }
        return true;
    }
    
    public GameActor createActor(String resource) {
        GameActor actor = new GameActor(getNextID(), resource);
        
        NodeList attributes = Application.get().getResourceManager().getDataManager().getData(resource);
        
        actor.createFromXML(attributes);
        
        Application.get().getEventManager().queueEvent(new ActorCreatedEvent(actor.getID()));
        
        return actor;
    }
    
    public long getNextID() {
        lastID++;
        return lastID;
    }
}
