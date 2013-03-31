package settii.views.humanView.audioPlayer;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.ActorDestroyedEvent;
import settii.eventManager.events.IGameEvent;

/**
 *
 * @author Merioksan Mikko
 */
public class ActorDestroyedListener implements IGameEventListener {
    private AudioPlayer player;
    
    public ActorDestroyedListener(AudioPlayer ap) {
        player = ap;
    }
    
    @Override
    public void execute(IGameEvent event) {
        ActorDestroyedEvent ade = (ActorDestroyedEvent)event;
        
        player.actorDestroyedListener(ade.getActor().getID());
    }
}
