import java.util.concurrent.*;

class DownloadThread extends Thread {
    private int number;
    private String url;

    public DownloadThread() {
        this.number = -1;
        this.url = 0;
    }

    public void run() {

    }

    public void setDownloadInfo(int num, String url) {
        this.number = num;
        this.url = url;
    }
}