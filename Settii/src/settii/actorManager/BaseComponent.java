package settii.actorManager;

/**
 * All actors in the game are composed of components with different attributes. All of those components extends this base class.
 * 
 * @author Merioksan Mikko
 */
public class BaseComponent {
    /**
     * EVERY component should override this!
     * 
     * @return the name of the component
     */
    public String getName() {
        return "BaseComponent";
    }
}
