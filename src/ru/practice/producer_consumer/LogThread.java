package ru.practice.producer_consumer;

public class LogThread extends Thread{

    private LogService logService;

    public LogThread(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    synchronized (this) {
                        if (logService.isShutDown() && logService.getReservations() == 0) {
                           break;
                        }
                    }
                    String msg = logService.getQueue().take();
                    synchronized (this) { logService.setReservations(logService.getReservations()-1);}
                    logService.getWriter().println(msg);
                } catch (InterruptedException e) {
                    // повторная попытка
                    throw new RuntimeException(e);
                }
            }
        } finally {
            logService.getWriter().close();
        }
    }
}
