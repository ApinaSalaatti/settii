package settii.processManager;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * ProcessManager manages all kinds of sweet processes (i.e. stuff that takes many frames to complete like movement or something)
 * 
 * @author Merioksan Mikko
 */
public class ProcessManager {
    private ArrayList<BaseProcess> processes;
    
    public ProcessManager() {
        processes = new ArrayList<BaseProcess>();
    }
    
    public void attachProcess(BaseProcess bp) {
        processes.add(bp);
    }
    
    public void removeAllProcesses() {
        processes.clear();
    }
    
    public void update(long deltaMs) {
        ArrayList<BaseProcess> toAttach = new ArrayList<BaseProcess>();
        
        Iterator<BaseProcess> it = processes.iterator();
        
        while(it.hasNext()) {
            BaseProcess current = it.next();
            
            if(current.getState() == BaseProcess.ProcessState.UNINITIALIZED) {
                current.init();
            }
            
            if(current.getState() == BaseProcess.ProcessState.RUNNING) {
                current.update(deltaMs);
            }
            
            if(current.isDead()) {
                switch(current.getState()) {
                    case SUCCEEDED:
                        current.onSuccess();
                        BaseProcess child = current.getChild();
                        if(child != null) {
                            toAttach.add(child);
                        }
                        break;
                    case FAILED:
                        current.onFail();
                        break;
                    case ABORTED:
                        current.onAbort();
                        break;
                }
                it.remove();
            }
        }
        
        for(BaseProcess p : toAttach) {
            attachProcess(p);
        }
    }
}
