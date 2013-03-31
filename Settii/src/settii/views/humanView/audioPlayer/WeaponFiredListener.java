package settii.views.humanView.audioPlayer;

import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.WeaponFiredEvent;

/**
 *
 * @author Merioksan Mikko
 */
public class WeaponFiredListener implements IGameEventListener {
    private AudioPlayer player;
    
    public WeaponFiredListener(AudioPlayer ap) {
        player = ap;
    }
    
    @Override
    public void execute(IGameEvent event) {
        WeaponFiredEvent wfe = (WeaponFiredEvent)event;
        player.weaponFiredListener(wfe.getActor());
    }
}
