package settii.views;

/**
 *
 * @author Merioksan Mikko
 */
public interface IGameView {
    public void update(long deltaMs);
    public boolean onKeyDown(int key);
    public boolean onKeyUp(int key);
    public boolean onMouseDown(int mX, int mY, int button);
    public boolean onMouseUp(int mX, int mY, int button);
    public boolean onPointerMove(int mDX, int mDY);
}
