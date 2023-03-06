import java.util.LinkedList;
import java.util.Queue;

public class Mutex {

    SemValue value = SemValue.One;
    Queue<Process> queue = new LinkedList<>();
    int ownerID;

    public void semWait(Process p) {
        if (value == SemValue.One) {
            ownerID = p.processID;
            value = SemValue.Zero;
        } else {
            /* place this process in queue */
            queue.add(p);
            /* block this process */
            p.setProcessState(ProcessState.Waiting);
            p.suspend();
        }
    }

    public void semSignal(Process p) {
        /*check if this process is the owner*/
        if (ownerID == p.processID) {
            if (queue.isEmpty())
                value = SemValue.One;
            else {
                /* remove a process from queue */
                Process removedP = queue.remove();
                /* continue this process */
                // We know that the process should only go to ready queue & the Dispatcher handles it, but we are using FCFS
                // We continue the process right away so that we can test the semaphores
                removedP.setProcessState(ProcessState.Running);
                ownerID = removedP.processID;
                removedP.resume();
            }
        }
    }
}

