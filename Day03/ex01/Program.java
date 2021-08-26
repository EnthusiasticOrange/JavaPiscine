import java.util.concurrent.Semaphore;

public class Program {
    public static void main(String[] args) {
        if ((args.length != 1)
                || (args.length == 1 && !args[0].startsWith("--count="))) {
            System.out.println("Usage: Program --count=NUMBER");
            return;
        }

        Semaphore eggSem = new Semaphore(1);
        Semaphore henSem = new Semaphore(1);

        String countStr = args[0].substring("--count=".length());
        try {
            final int count = Integer.parseInt(countStr);

            Runnable eggRunnable = () -> {
                for (int i = 0; i < count; ++i) {
                    try {
                        eggSem.acquire();
                    } catch (InterruptedException e) {}
                    System.out.println("Egg");
                    henSem.release();
                }
            };

            Runnable henRunnable = () -> {
                for (int i = 0; i < count; ++i) {
                    try {
                        henSem.acquire();
                    } catch (InterruptedException e) {}
                    System.out.println("Hen");
                    eggSem.release();
                }
            };

            Thread eggThread = new Thread(eggRunnable);
            Thread henThread = new Thread(henRunnable);

            henSem.acquire();
            eggThread.start();
            henThread.start();

            eggThread.join();
            henThread.join();
        } catch (NumberFormatException e) {
            System.err.printf("%s is not a number\n", countStr);
            return;
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted");
            return;
        }
    }
}