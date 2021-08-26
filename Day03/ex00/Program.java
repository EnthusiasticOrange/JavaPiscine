public class Program {
    public static void main(String[] args) {
        if ((args.length != 1)
                || (args.length == 1 && !args[0].startsWith("--count="))) {
            System.out.println("Usage: Program --count=NUMBER");
            return;
        }

        String countStr = args[0].substring("--count=".length());
        try {
            final int count = Integer.parseInt(countStr);

            Runnable eggRunnable = () -> {
                for (int i = 0; i < count; ++i) {
                    System.out.println("Egg");
                }
            };

            Runnable henRunnable = () -> {
                for (int i = 0; i < count; ++i) {
                    System.out.println("Hen");
                }
            };

            Thread eggThread = new Thread(eggRunnable);

            Thread henThread = new Thread(henRunnable);

            eggThread.start();
            henThread.start();

            eggThread.join();
            henThread.join();

            for (int i = 0; i < count; ++i) {
                System.out.println("Human");
            }
        } catch (NumberFormatException e) {
            System.err.printf("%s is not a number\n", countStr);
            return;
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted");
            return;
        }
    }
}