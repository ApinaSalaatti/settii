package settii.eventManager;

import settii.eventManager.events.IGameEvent;

/**
 * Interface that all event listener implement.
 * 
 * @author Merioksan Mikko
 */
public interface IGameEventListener {
    public void execute(IGameEvent e);
}
