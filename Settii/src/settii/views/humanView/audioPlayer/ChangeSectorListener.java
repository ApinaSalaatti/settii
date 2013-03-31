package settii.views.humanView.audioPlayer;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.ChangeSectorEvent;
import settii.eventManager.events.IGameEvent;

/**
 *
 * @author Merioksan Mikko
 */
public class ChangeSectorListener implements IGameEventListener {
    private AudioPlayer player;
    
    public ChangeSectorListener(AudioPlayer ap) {
        player = ap;
    }
    
    @Override
    public void execute(IGameEvent event) {
        ChangeSectorEvent cse = (ChangeSectorEvent)event;
        player.changeSectorListener(cse.getID());
    }
}
