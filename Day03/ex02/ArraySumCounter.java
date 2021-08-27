import java.util.*;
import java.util.stream.*;

class ArraySumCounter {
    private List<ArraySumCounterThread> threadList;
    private int threadsCount;
    private int[] array;

    public ArraySumCounter(int arraySize, int threadsCount) {
        this.threadsCount = threadsCount;
        this.threadList = new ArrayList<>();

        Random rand = new Random();
        this.array = IntStream.generate(() -> rand.nextInt(10))
                        .limit(arraySize)
                        .toArray();
    }

    public void exec() {
        System.out.printf("Sum: %d\n", IntStream.of(this.array).sum());

        int arrSection = (int) Math.ceil((double) this.array.length / this.threadsCount);
        int start = 0;
        for (int i = 0; i < this.threadsCount; ++i) {
            int end = start + arrSection;
            if (end > this.array.length) {
                end = this.array.length;
            }
            ArraySumCounterThread counter = new ArraySumCounterThread(i + 1, this.array, start, end - 1);
            start += arrSection;

            counter.start();
            this.threadList.add(counter);
        }

        try {
            for (ArraySumCounterThread t : this.threadList) {
                t.join();
            }
        } catch (InterruptedException e) {
            System.err.println("Error: Thread has been interrupted");
            return;
        }

        long threadSum = this.threadList.stream()
                            .mapToInt(ArraySumCounterThread::getSum)
                            .sum();
        System.out.printf("Sum by threads: %d\n", threadSum);
    }
}