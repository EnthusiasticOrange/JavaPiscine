import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        if (num <= 1) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }

        int op = 1;
        boolean isPrime = true;

        for (int i = 2; i * i <= num; i += 1) {
            if (num % i == 0) {
                isPrime = false;
                break;
            }
            op += 1;
        }
        
        System.out.printf("%b %d\n", isPrime, op);
    }
}