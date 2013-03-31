package settii.views.humanView.audioPlayer;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;

/**
 *
 * @author Merioksan Mikko
 */
public class OptionsChangedListener implements IGameEventListener {
    private AudioPlayer player;
    
    public OptionsChangedListener(AudioPlayer ap) {
        player = ap;
    }
    
    @Override
    public void execute(IGameEvent event) {
        player.optionsChangedListener();
    }
}
