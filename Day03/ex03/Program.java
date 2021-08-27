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

        ThreadPool pool = new ThreadPool(threadsCount);

        if (!pool.readFile("files_urls.txt")) {
            return;
        }

        System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
        pool.exec();
    }
}