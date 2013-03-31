package settii.logic.messaging;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.NewMessageEvent;

/**
 *
 * @author Merioksan Mikko
 */
public class NewMessageListener implements IGameEventListener {
    private Messaging messaging;
    
    public NewMessageListener(Messaging m) {
        messaging = m;
    }
    
    @Override
    public void execute(IGameEvent event) {
        NewMessageEvent nme = (NewMessageEvent)event;
        
        messaging.newMessageListener(nme.getMessage());
    }
}
