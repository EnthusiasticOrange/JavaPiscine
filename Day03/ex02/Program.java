public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: Program --arraySize=NUMBER --threadsCount=NUMBER");
            return;
        }

        int arraySize = -1;
        int threadsCount = -1;

        try {
            for (String arg : args) {
                if (arg.startsWith("--arraySize=")) {
                    String arrStr = arg.substring("--arraySize=".length());
                    arraySize = Integer.parseInt(arrStr);
                } else if (arg.startsWith("--threadsCount=")) {
                    String arrStr = arg.substring("--threadsCount=".length());
                    threadsCount = Integer.parseInt(arrStr);
                } else {
                    System.out.println("Usage: Program --arraySize=NUMBER --threadsCount=NUMBER");
                    return;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Usage: Program --arraySize=NUMBER --threadsCount=NUMBER");
            return;
        }

        if (arraySize == -1 || threadsCount == -1) {
            System.out.println("Usage: Program --arraySize=NUMBER --threadsCount=NUMBER");
            return;
        }

        ArraySumCounter counter = new ArraySumCounter(arraySize, threadsCount);
        counter.exec();
    }
}