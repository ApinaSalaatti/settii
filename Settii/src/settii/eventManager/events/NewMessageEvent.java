package settii.eventManager.events;

import settii.logic.messaging.Message;

/**
 *
 * @author Merioksan Mikko
 */
public class NewMessageEvent implements IGameEvent {
    public static long eventType = 30;
    private Message message;
    
    public NewMessageEvent(Message m) {
        message = m;
    }
    
    @Override
    public long getEventType() {
        return eventType;
    }
    
    public Message getMessage() {
        return message;
    }
}
