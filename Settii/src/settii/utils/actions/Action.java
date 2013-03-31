package settii.utils.actions;

/**
 *
 * @author Merioksan Mikko
 */
public abstract class Action {
    public abstract void update(long deltaMs);
    public abstract boolean finished();
}
