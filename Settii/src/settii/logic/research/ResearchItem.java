package settii.logic.research;

import java.util.Collection;
import java.util.HashMap;
import settii.Application;
import settii.logic.shop.Sellable;
import settii.eventManager.events.researchEvents.ResearchEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class ResearchItem implements Sellable {
    private String name, description;
    private String sprite;
    
    private HashMap<String, ResearchItem> prerequisites;
    private HashMap<String, ResearchItem> prerequisiteOf;
    
    private boolean researched;
    private int cost;
    
    private ResearchEvent event;
    
    public ResearchItem(String name, ResearchEvent event) {
        this(name, event, 0);
    }
    
    public ResearchItem(String name, ResearchEvent event, int cost) {
        this(name, event, "assets/graphics/research/research.png", cost);
    }
    
    public ResearchItem(String name, ResearchEvent event, String sprite, int cost) {
        this(name, "An interesting research!", event, sprite, cost);
    }
    
    public ResearchItem(String name, String description, ResearchEvent event, String sprite, int cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.event = event;
        this.sprite = sprite;
        
        researched = false;
        prerequisites = new HashMap<String, ResearchItem>();
        prerequisiteOf = new HashMap<String, ResearchItem>();
    }
    
    public void setValue(int c) {
        cost = c;
    }
    @Override
    public int getValue() {
        return cost;
    }
    
    public String getName() {
        return name;
    }
    
    public void setDescription(String d) {
        description = d;
    }
    public String getDescription() {
        return description;
    }
    
    public void addPrerequisite(ResearchItem p) {
        prerequisites.put(p.getName(), p);
        //p.addFollower(this);
    }
    public Collection<ResearchItem> getPrerequisites() {
        return prerequisites.values();
    }
    
    public void addFollower(ResearchItem f) {
        prerequisiteOf.put(f.getName(), f);
        f.addPrerequisite(this);
    }
    public Collection<ResearchItem> getFollowers() {
        return prerequisiteOf.values();
    }
    
    public String getSprite() {
        return sprite;
    }
    
    public boolean researched() {
        return researched;
    }
    
    @Override
    public boolean buy() {
        if(researched) {
            return false;
        }
        Application.get().getEventManager().queueEvent(event);
        researched = true;
        return true;
    }
}
