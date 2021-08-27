public class Program {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: Program --threadsCount=NUMBER");
            return;
        }

        int threadsCount = -1;
        try {
            if (args[0].startsWith("--threadsCount=")) {
                threadsCount = Integer.parseInt(args[0].substring("--threadsCount=".length()));
            }
        } catch (NumberFormatException e) {
            System.out.println("Usage: Program --threadsCount=NUMBER");
            return;
        }

        if (threadsCount == -1) {
            System.out.println("Usage: Program --threadsCount=NUMBER");
            return;
        }

        ExecutorService pool = Executors.newFixedThreadPool(threadsCount);
    }
}