import java.util.concurrent.*;

class ArraySumCounterThread extends Thread {
    private int id;
    private int[] array;
    private int start;
    private int end;
    private int sum;

    public ArraySumCounterThread(int id, int[] array, int start, int end) {
        this.id = id;
        this.array = array;
        this.start = start;
        this.end = end;
        this.sum = 0;
    }

    public int getSum() {
        return this.sum;
    }

    public void run() {
        for (int i = start; i <= end; ++i) {
            sum += array[i];
        }
        System.out.printf("Thread %d: from %d to %d sum is %d\n", id, start, end, sum);
    }
}