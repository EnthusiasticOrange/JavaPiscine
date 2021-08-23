import java.util.Scanner;

public class Program {
    public static int digitSum(int num) {
        int res = 0;

        while (num > 0) {
            res += num % 10;
            num /= 10;
        }
        return res;
    }

    public static boolean isPrime(int num) {
        for (int i = 2; i * i <= num; ++i) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int requestCount = 0;

        int requset = 0;

        while ((requset = s.nextInt()) != 42) {
            int sum = digitSum(requset);

            if (isPrime(sum)) {
                requestCount += 1;
            }
        }

        System.out.printf("Count of coffee-request - %d\n", requestCount);
    }
}