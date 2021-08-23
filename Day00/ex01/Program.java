import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int opCount = 1;

        boolean isPrime = true;

        int num = s.nextInt();

        if (num <= 1) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }

        for (int i = 2; i * i <= num; ++i) {
            if (num % i == 0) {
                isPrime = false;
                break;
            }
            opCount += 1;
        }
        
        System.out.printf("%b %d\n", isPrime, opCount);
    }
}