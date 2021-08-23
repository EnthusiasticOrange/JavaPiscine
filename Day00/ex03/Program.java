import java.util.Scanner;

public class Program {
    private static final int GRADE_COUNT = 5;

    private static final int MAX_WEEK = 18;

    public static void printWeek(long storage, int weekNum) {
        int grade = (int) (storage % 10);

        if (storage == 0)
            return;

        printWeek(storage / 10, weekNum - 1);

        System.out.printf("Week %d ", weekNum);

        for (int i = 0; i < grade; ++i) {
            System.out.print("=");
        }
        System.out.println(">");
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String input = "";

        String output = "";

        long gradeStorage = 0;

        int weekNum = 0;

        while (true) {
            Scanner intS = new Scanner(System.in);

            int minGrade = 9;

            input = s.nextLine();
            if (input.equals("42")) {
                break;
            }

            weekNum += 1;
            if (!input.equals("Week " + weekNum)) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }

            for (int i = 0; i < GRADE_COUNT; ++i) {
                int grade = 0;

                if (!intS.hasNextInt()) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }

                grade = intS.nextInt();
                if (grade < 1 || grade > 9) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }

                if (minGrade > grade) {
                    minGrade = grade;
                }
            }

            gradeStorage = gradeStorage * 10 + minGrade;
            if (weekNum == MAX_WEEK) {
                break;
            }
        }

        printWeek(gradeStorage, weekNum);
    }
}