package settii.actorManager;

import settii.actorManager.GameActor;
import org.w3c.dom.NodeList;

/**
 * All actors in the game are composed of components with different attributes. All of those components extends this base class.
 * 
 * @author Merioksan Mikko
 */
public class BaseComponent {
    protected GameActor owner;
    
    /**
     * EVERY component should override this!
     * 
     * @return the name of the component
     */
    public String getName() {
        return "BaseComponent";
    }
    
    public void setOwner(GameActor actor) {
        owner = actor;
    }
    public GameActor getOwner() {
        return owner;
    }
    
    public void createFromXML(NodeList attributes) {
        System.out.println("ERROR! Cannot create a base component.");
    }
    
    /**
     * Override this in extending components if needed.
     * 
     * @param deltaMs elapsed time since last update
     */
    public void update(long deltaMs) {
        
    }
    
    /**
     * Copies a components attributs to another component. Override this shit in components!
     * 
     * @param bc the component to copy this component's attributes to
     */
    public void copyTo(BaseComponent bc) {
        
    }
}
