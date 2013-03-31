package settii.utils.actions;

/**
 *
 * @author Merioksan Mikko
 */
public class DelayAction extends Action {
    private long delay, elapsed;
    
    public DelayAction(long d) {
        delay = d;
        elapsed = 0;
    }
    
    @Override
    public void update(long deltaMs) {
        elapsed += deltaMs;
    }
    
    @Override
    public boolean finished() {
        return elapsed >= delay;
    }
}
