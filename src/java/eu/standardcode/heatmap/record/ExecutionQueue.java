package eu.standardcode.heatmap.record;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
class ExecutionQueue implements Runnable {

    static final ExecutionQueue INSTANCE = new ExecutionQueue();

    static {
        new Thread(INSTANCE).start();
    }
    private static final Logger logger = Logger.getLogger(ExecutionQueue.class.getName());
    private final Queue<Process> queue = new ArrayBlockingQueue<>(100);
    private int max = 0;

    private ExecutionQueue() {
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                try {
                    queue.wait();
                } catch (InterruptedException ex) {
                    return;
                }
            }
            do {
                Process first;
                synchronized (queue) {
                    first = queue.poll();
                }
                if (first == null) {
                    break;
                }
                first.run();
            } while (true);
        }
    }

    void enqueue(Process pr) {
        int s = 0;
        synchronized (queue) {
            if (queue.offer(pr)) {
                s = queue.size();
                queue.notifyAll();
            }
        }
        if (s > max) {
            max = s;
            logger.log(Level.INFO, "Max queue elements: {0}", max);
        }
    }
}
