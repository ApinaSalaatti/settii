package settii.utils.actions;

import java.util.ArrayDeque;
import settii.Application;

/**
 *
 * @author Merioksan Mikko
 */
public class Sequence<T> {
    public static int TIMED_SEQUENCE = 1;
    public static int RANDOM_SEQUENCE = 2;
    public static int MANUAL_SEQUENCE = 3;
    
    private int type;
    private long delay, elapsed;
    private ArrayDeque<T> sequence;
    
    public Sequence(int type, T ... actions) {
        sequence = new ArrayDeque<T>();
        for(T t : actions) {
            sequence.addLast(t);
        }
        
        this.type = type;
        if(type == TIMED_SEQUENCE) {
            delay = 1000; // if delay is not given for timed sequence, default to one second
        }
    }
    
    public Sequence(int type, long delay, T ... actions) {
        sequence = new ArrayDeque<T>();
        for(T t : actions) {
            sequence.addLast(t);
        }
        
        this.type = type;
        this.delay = delay;
    }
    
    public void update(long deltaMs) {
        elapsed += deltaMs;
    }
    
    public void addAction(T action) {
        sequence.addLast(action);
    }
    
    public T getCurrent() {
        if(type == TIMED_SEQUENCE) {
            if(elapsed >= delay) {
                sequence.pollFirst();
                elapsed = 0;
            }
            return sequence.peekFirst();
        }
        else if(type == RANDOM_SEQUENCE) {
            int r = Application.get().getRNG().nextInt(sequence.size());
            T t = (T)sequence.toArray()[r];
            sequence.remove(t);
            return t;
        }
        else if(type == MANUAL_SEQUENCE) {
            return sequence.peekFirst();
        }
        
        return null;
    }
    
    public void next() {
        sequence.pollFirst();
    }
    
    public boolean empty() {
        return sequence.size() <= 0;
    }
}
