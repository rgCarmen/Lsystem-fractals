package branch;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Monitor {
    private final BlockingQueue<Line> queue = new LinkedBlockingQueue<>();

    public void addLine(Line line) {
        try {
            queue.put(line);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Line getLine() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

