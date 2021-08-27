import java.util.concurrent.*;

class ThreadPool {
    class DownloadInfo {
        public int number;
        public String url;

        public DownloadInfo(int num, String url) {
            this.number = num;
            this.url = url;
        }
    }

    private Queue<DownloadInfo> queue;
    private DownloadThread[] workers;

    public ThreadPool(int threadsCount) {
        this.workers = new DownloadThread[threadsCount];
        this.queue = new BlockingQueue<>();
    }

    public boolean readFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                String[] split = line.trim().split("\\s+");

                if (split.length != 2) {
                    System.err.println("Error parsing link from file");
                    return false;
                }

                int num = Integer.parseInt(split[0]);
                DownloadInfo info = new DownloadInfo(num, split[1]);
                queue.add(info);
            }
        } catch (IOException e) {
            System.err.printf("Error reading '%s'\n", filename);
            return false;
        }
        catch (NumberFormatException e) {
            System.err.printf("Error reading '%s'\n", filename);
            return false;
        }
        return true;
    }

    public void exec() {
        while ()
    }
}