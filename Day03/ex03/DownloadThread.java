import java.util.concurrent.*;
import java.io.*;
import java.nio.file.*;
import java.nio.channels.*;
import java.net.*;

class DownloadThread extends Thread {
    private BlockingQueue<DownloadInfo> jobQueue;

    public DownloadThread(BlockingQueue<DownloadInfo> queue) {
        this.jobQueue = queue;
    }

    public void run() {
        while (true) {
            synchronized (this.jobQueue) {
                if (this.jobQueue.isEmpty()) {
                    return;
                }
            }
            try {
                DownloadInfo info = this.jobQueue.take();
                System.out.printf("%s start download file number %d\n", this.getName(), info.number);

                String fileName = info.url.substring(info.url.lastIndexOf('/') + 1);
                URL dlUrl;
                try {
                    dlUrl = new URL(info.url);
                } catch (MalformedURLException e) {
                    System.err.printf("ERROR (%s): file %d URL is malformed\n", this.getName(), info.number);
                    continue;
                }

                try (InputStream urlStream = dlUrl.openStream()) {
                    Files.copy(urlStream, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.err.printf("ERROR (%s): download file %d failed: %s\n", this.getName(), info.number, e.getMessage());
                }
            } catch (InterruptedException e) {
                System.out.printf("%s caught InterruptedException\n", this.getName());
            }
        }
    }
}