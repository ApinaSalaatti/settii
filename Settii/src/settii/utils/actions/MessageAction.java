package settii.utils.actions;

import settii.Application;
import settii.eventManager.events.NewMessageEvent;
import settii.logic.messaging.Message;

/**
 *
 * @author Merioksan Mikko
 */
public class MessageAction extends Action {
    private boolean sent;
    private Message message;
    
    public MessageAction(Message m) {
        message = m;
        sent = false;
    }
    
    @Override
    public void update(long deltaMs) {
        Application.get().getEventManager().queueEvent(new NewMessageEvent(message));
        sent = true;
    }
    
    @Override
    public boolean finished() {
        return sent;
    }
}
