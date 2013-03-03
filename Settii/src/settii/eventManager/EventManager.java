package settii.eventManager;

import settii.eventManager.events.IGameEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import settii.eventManager.events.ActorDestroyedEvent;
/**
 * Class that manages all sorts of game events!
 * 
 * @author Merioksan Mikko
 */
public class EventManager {
    private static long lastEventType = 22; // LOL just for me to remember it :---------)
    
    private static EventManager instance = new EventManager();
    private HashMap<Long, ArrayList<IGameEventListener>> eventListeners;
    private ArrayList<ArrayDeque<IGameEvent>> eventQueues;
    private int currentQueue;
    
    public static EventManager get() {
        return instance;
    }
    
    public EventManager() {
        this.eventListeners = new HashMap<Long, ArrayList<IGameEventListener>>();
        this.eventQueues = new ArrayList<ArrayDeque<IGameEvent>>();
        eventQueues.add(new ArrayDeque<IGameEvent>());
        eventQueues.add(new ArrayDeque<IGameEvent>());
        currentQueue = 0;
    }
    
    public void register(long eventType, IGameEventListener listener) {
        if(!eventListeners.containsKey(eventType)) {
            eventListeners.put(eventType, new ArrayList<IGameEventListener>());
        }
        
        eventListeners.get(eventType).add(listener);
    }
    
    public void executeEvent(IGameEvent e) {
        ArrayList<IGameEventListener> listeners = eventListeners.get(e.getEventType());
        
        if(listeners != null) {
            for(IGameEventListener listener : listeners) {
                listener.execute(e);
            }
        }
    }
    
    public void queueEvent(IGameEvent e) {
        eventQueues.get(currentQueue).addLast(e);
    }
    
    public void update() {
        int current = currentQueue;
        currentQueue = (currentQueue + 1) % 2;
        eventQueues.get(currentQueue).clear();
        
        ArrayDeque<IGameEvent> toEmpty = eventQueues.get(current);
        
        while(!toEmpty.isEmpty()) {
            IGameEvent event = toEmpty.pollFirst();
            executeEvent(event);
        }
    }
    
    public boolean init() {
        return true;
    }
}
