/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package settii.views.humanView.listeners;

import settii.views.humanView.GameScene;
import settii.eventManager.IGameEventListener;
import settii.eventManager.events.IGameEvent;
import settii.eventManager.events.ActorTextureChangeEvent;
/**
 *
 * @author Merioksan Mikko
 */
public class ActorTextureChangeListener implements IGameEventListener {
    private GameScene scene;
    
    public ActorTextureChangeListener(GameScene scene) {
        this.scene = scene;
    }
    
    @Override
    public void execute(IGameEvent event) {
        ActorTextureChangeEvent atce = (ActorTextureChangeEvent)event;
        scene.ActorTextureChangeListener(atce);
    }
}
