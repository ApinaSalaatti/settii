package settii.processManager;

/**
 * A base that all game processes inherit from. Inheriting classes override methods as needed.
 * 
 * @author Merioksan Mikko
 */
public class BaseProcess {
    public enum ProcessState { UNINITIALIZED, RUNNING, PAUSED, SUCCEEDED, FAILED, ABORTED };
    
    protected ProcessState currentState;
    protected BaseProcess child;
    
    public BaseProcess() {
        currentState = ProcessState.UNINITIALIZED;
    }
    
    public void init() {
        currentState = ProcessState.RUNNING;
    }
    
    public void onSuccess() {
        
    }
    
    public void onFail() {
        
    }
    
    public void onAbort() {
        
    }
    
    public void update(long deltaMs) {
        
    }
    
    public void succeed() {
        currentState = ProcessState.SUCCEEDED;
    }
    public void fail() {
        currentState = ProcessState.FAILED;
    }
    
    public ProcessState getState() {
        return currentState;
    }
    
    public boolean isAlive() {
        return currentState == ProcessState.PAUSED || currentState == ProcessState.RUNNING;
    }
    public boolean isDead() {
        return currentState == ProcessState.ABORTED || currentState == ProcessState.FAILED || currentState == ProcessState.SUCCEEDED;
    }
    
    public void Pause() {
        currentState = ProcessState.PAUSED;
    }
    public void unPause() {
        currentState = ProcessState.RUNNING;
    }
    public boolean isPaused() {
        return currentState == ProcessState.PAUSED;
    }
    
    public void attachChild(BaseProcess child) {
        this.child = child;
    }
    public BaseProcess getChild() {
        return child;
    }
    public BaseProcess removeChild() {
        BaseProcess c = child;
        child = null;
        return c;
    }
}
