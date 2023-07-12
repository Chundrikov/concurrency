package ru.practice.producer_consumer;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

/**
 *
 */
public class LogService {
    private final BlockingQueue<String> queue;
    private final LogThread logThread;
    private final PrintWriter writer;
    private boolean isShutDown;
    private int reservations;

    public LogService(BlockingQueue<String> queue, LogThread logThread, PrintWriter writer) {
        this.queue = queue;
        this.logThread = logThread;
        this.writer = writer;
    }

    public void start() {
        logThread.start();
    }

    public void stop() {
        synchronized (this) {
            isShutDown = true;
        }
        logThread.interrupt();
    }

    public void log(String message) throws InterruptedException {
        synchronized (this) {
            if (isShutDown) {
                throw new InterruptedException();
            }
            reservations++;
        }
        queue.put(message);
    }

    public boolean isShutDown() {
        return isShutDown;
    }

    public int getReservations() {
        return reservations;
    }

    public BlockingQueue<String> getQueue() {
        return queue;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setReservations(int reservations) {
        this.reservations = reservations;
    }
}
