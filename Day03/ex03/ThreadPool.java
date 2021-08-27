import java.util.*;
import java.io.*;
import java.util.concurrent.*;

class ThreadPool {
    private List<DownloadThread> workerList;
    private BlockingQueue<DownloadInfo> queue;

    public ThreadPool(int threadsCount) {
        this.workerList = new ArrayList<>();
        this.queue = new LinkedBlockingQueue<>();

        for (int i = 0; i < threadsCount; ++i) {
            DownloadThread t = new DownloadThread(this.queue);
            this.workerList.add(t);
        }
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
        for (DownloadThread t : this.workerList) {
            t.start();
        }

        for (DownloadThread t : this.workerList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.printf("%s has been interrupted\n", t.getName());
            }
        }
    }
}