import java.util.Scanner;

public class Program {
    private static final int MAX_CODE_VALUE = 65535;

    private static final int MAX_CHART_CHARS = 10;

    private static final int MAX_FREQ = 40;

    private static final int PAIR_OF_VALS = 2;

    private static final int CHAR = 0;

    private static final int FREQ = 1;

    public static String someAsciiToStr(char c) {
        switch (c) {
            case 7:
                return "BEL";
            case 8:
                return "BS";
            case 9:
                return "HT";
            case 10:
                return "LF";
            case 11:
                return "VT";
            case 12:
                return "FF";
            case 13:
                return "CR";
            case 27:
                return "ESC";
            case 32:
                return "SP";
            default:
                return "NON";
        }
    }

    public static void printChart(char[][] printArr) {
        int colSize = 0;

        for (int i = 0; i < MAX_CHART_CHARS; ++i) {
            if (printArr[i][FREQ] == 0) {
                break;
            }

            System.out.printf("%4d", (int) printArr[i][FREQ]);

            if (printArr[i][FREQ] > MAX_FREQ) {
                printArr[i][FREQ] = MAX_FREQ;
            }

            while ((printArr[i][FREQ] / 4) > (printArr[i + 1][FREQ] / 4)) {
                if (printArr[i][FREQ] < 4) {
                    break;
                }

                System.out.println();

                for (int j = 0; j <= i; ++j) {
                    System.out.print("   #");
                }

                printArr[i][FREQ] -= 4;
            }
            colSize += 1;
        }

        System.out.println();

        for (int j = 0; j < colSize; ++j) {
            if ((printArr[j][CHAR] < 33) || (printArr[j][CHAR] == 127)) {
                System.out.printf("%4s", someAsciiToStr(printArr[j][CHAR]));
            } else {
                System.out.printf("%4c", printArr[j][CHAR]);
            }
        }

        System.out.println();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String input = s.nextLine();

        char[] freqArr = new char[MAX_CODE_VALUE];

        char[] inputArr = input.toCharArray();

        char[][] printArr = new char[MAX_CHART_CHARS + 1][PAIR_OF_VALS];

        for (int i = 0; i < input.length(); ++i) {
            freqArr[inputArr[i]] += 1;
        }

        for (int i = 0; i < MAX_CODE_VALUE; ++i) {
            char c = (char) i;

            char freq = freqArr[i];

            if (freqArr[i] == 0) {
                continue;
            }

            for (int j = 0; j < MAX_CHART_CHARS; j++) {
                if (printArr[j][FREQ] < freq) {
                    char tmpC = printArr[j][CHAR];

                    char tmpFreq = printArr[j][FREQ];

                    printArr[j][CHAR] = c;
                    c = tmpC;

                    printArr[j][FREQ] = freq;
                    freq = tmpFreq;
                }
            }
        }

        printChart(printArr);
    }
}