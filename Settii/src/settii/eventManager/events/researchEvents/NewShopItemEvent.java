package settii.eventManager.events.researchEvents;

/**
 *
 * @author Merioksan Mikko
 */
public class NewShopItemEvent extends ResearchEvent {
    public static long eventType = 23;
    
    private String resource;
    
    public NewShopItemEvent(String res) {
        resource = res;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public String getResource() {
        return resource;
    }
}
