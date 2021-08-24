public class Program {
    public static void main(String[] args) {
        int number = 479598;

        int r1 = number % 10;

        int r2 = (number / 10) % 10;

        int r3 = (number / 100) % 10;

        int r4 = (number / 1000) % 10;

        int r5 = (number / 10000) % 10;

        int r6 = (number / 100000) % 10;

        int result = r1 + r2 + r3 + r4 + r5 + r6;

        System.out.println(result);
    }
}