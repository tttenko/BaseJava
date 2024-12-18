public class Deadlock extends Thread {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread_1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println(Thread.currentThread() + " захватил lock1");
                sleeps();
                synchronized ((lock2)) {
                    System.out.println(Thread.currentThread() + " захватил lock2");
                }
            }
        });

        Thread thread_2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println(Thread.currentThread() + " захватил lock1");
                sleeps();
                synchronized ((lock1)) {
                    System.out.println(Thread.currentThread() + " захватил lock2");
                }
            }
        });

        thread_1.start();
        thread_2.start();
    }
    private final static void sleeps() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

