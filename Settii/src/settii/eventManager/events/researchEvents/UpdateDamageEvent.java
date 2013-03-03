package settii.eventManager.events.researchEvents;

/**
 *
 * @author Merioksan Mikko
 */
public class UpdateDamageEvent extends ResearchEvent {
    public static long eventType = 21;
    
    String actorRes;
    int damageToAdd;
    
    public UpdateDamageEvent(String resource, int damage) {
        actorRes = resource;
        damageToAdd = damage;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public String getResource() {
        return actorRes;
    }
    public int getDamageToAdd() {
        return damageToAdd;
    }
}
