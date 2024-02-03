package homeworks.task2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Task2 {
    public static void main(String[] args) {
        AtomicInteger amount = new AtomicInteger();
        Random random = new Random();
        MyThread thread1 = new MyThread(() -> {
            System.out.println(amount.get());
            amount.addAndGet(random.nextInt(0, 5));
        });
        MyThread thread2 = new MyThread(() -> {
            int r = random.nextInt(0, 5);
            if (amount.get() >= r) amount.addAndGet(-r);
            else System.out.println("Не хватает!");
        });
        thread2.start();
        thread1.start();
    }
}
