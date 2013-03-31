package settii.logic.messaging;

import java.util.ArrayDeque;
import settii.Application;
import settii.eventManager.events.NewMessageEvent;

/**
 *
 * @author Merioksan Mikko
 */
public class Messaging {
    private ArrayDeque<Message> messageQueue;
    
    public Messaging() {
        messageQueue = new ArrayDeque<Message>();
        
        Application.get().getEventManager().register(NewMessageEvent.eventType, new NewMessageListener(this));
    }
    
    public void update(long deltaMs) {
        if(!messageQueue.isEmpty()) {
            messageQueue.peekFirst().update(deltaMs);
            
            if(messageQueue.peekFirst().finished()) {
                nextMessage();
            }
        }
    }
    
    public void nextMessage() {
        messageQueue.pollFirst();
    }
    
    public Message getCurrentMessage() {
        return messageQueue.peekFirst();
    }
    
    public void addMessage(Message m) {
        messageQueue.addLast(m);
    }
    public void addMessage(String topic, String content) {
        messageQueue.addLast(new Message(topic, content));
    }
    
    public void clear() {
        messageQueue.clear();
    }
    
    public void newMessageListener(Message m) {
        addMessage(m);
    }
}
