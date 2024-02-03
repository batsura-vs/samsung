package homeworks.task2;

public class MyThread extends Thread {
    private boolean isAlive = true;
    private final Executable task;

    public MyThread(Executable task) {
        this.task = task;
    }

    @Override
    public void run() {
        super.run();
        while (isAlive) {
            task.execute();
            try {
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cancel() {
        isAlive = false;
    }

    public interface Executable {
        void execute();
    }
}
