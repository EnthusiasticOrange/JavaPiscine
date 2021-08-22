import java.util.Scanner;

public class Program {
    public static void printWeek(long storage, int weekNum) {
        if (storage == 0)
            return;
        printWeek(storage / 10, weekNum - 1);
        System.out.printf("Week %d ", weekNum);
        int grade = (int)(storage % 10);
        for (int i = 0; i < grade; ++i) {
            System.out.print("=");
        }
        System.out.println(">");
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input;
        String output = "";
        long gradeStorage = 0;
        int weekNum = 0;

        while (true) {
            input = s.nextLine();
            if (input.equals("42")) {
                break;
            }
            weekNum += 1;
            if (!input.equals("Week " + weekNum)) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }

            Scanner intS = new Scanner(System.in);
            int minGrade = 9;
            for (int i = 0; i < 5; ++i) {
                int grade = intS.nextInt();
                if (minGrade > grade) {
                    minGrade = grade;
                }
            }

            gradeStorage = gradeStorage * 10 + minGrade;
            if (weekNum == 18) {
                break;
            }
        }

        printWeek(gradeStorage, weekNum);
    }
}