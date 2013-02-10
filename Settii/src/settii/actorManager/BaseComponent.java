package settii.actorManager;

import org.w3c.dom.NodeList;

/**
 * All actors in the game are composed of components with different attributes. All of those components extends this base class.
 * 
 * @author Merioksan Mikko
 */
public class BaseComponent {
    protected long owner;
    
    /**
     * EVERY component should override this!
     * 
     * @return the name of the component
     */
    public String getName() {
        return "BaseComponent";
    }
    
    public void setOwner(long id) {
        owner = id;
    }
    public long getOwner() {
        return owner;
    }
    
    public void createFromXML(NodeList attributes) {
        System.out.println("ERROR! Cannot create a base component.");
    }
}
