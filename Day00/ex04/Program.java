import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        short[] freqArr = new short[65535];

        char[] inputArr = input.toCharArray();
        for (int i = 0; i < input.length(); ++i) {
            freqArr[inputArr[i]] += 1;
        }

        short[][] printArr = new short[11][2];
        for (int i = 0; i < 65535; ++i) {
            if (freqArr[i] == 0) {
                continue;
            }
            char c = (char)i;
            short freq = freqArr[i];
            for (int j = 0; j < 10; j++) {
                if (printArr[j][1] < freq) {
                    char tmpC = (char)printArr[j][0];
                    printArr[j][0] = (short)c;
                    c = tmpC;

                    short tmpFreq = printArr[j][1];
                    printArr[j][1] = freq;
                    freq = tmpFreq;
                }
            }
        }

        int i;
        for (i = 0; i < 10; ++i) {
            if (printArr[i][1] == 0) {
                break;
            }
            System.out.printf("%4d", printArr[i][1]);
            if (printArr[i][1] > 40) {
                printArr[i][1] = 40;
            }
            while ((printArr[i][1] / 4) > (printArr[i + 1][1] / 4)) {
                if (printArr[i][1] < 4) {
                    break;
                }
                System.out.println();
                for (int j = 0; j <= i; ++j) {
                    System.out.print("   #");
                }
                printArr[i][1] -= 4;
            }
        }
        System.out.println();
        for (int j = 0; j < i; ++j) {
            System.out.printf("%4c", (char)printArr[j][0]);
        }
        System.out.println();
    }
}