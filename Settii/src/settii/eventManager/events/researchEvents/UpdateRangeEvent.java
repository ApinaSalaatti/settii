package settii.eventManager.events.researchEvents;

/**
 *
 * @author Merioksan Mikko
 */
public class UpdateRangeEvent extends ResearchEvent {
    public static long eventType = 24;
    
    private String resource;
    private int rangeToAdd;
    
    public UpdateRangeEvent(String res, int range) {
        resource = res;
        rangeToAdd = range;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public String getResource() {
        return resource;
    }
    public int getRangeToAdd() {
        return rangeToAdd;
    }
}
