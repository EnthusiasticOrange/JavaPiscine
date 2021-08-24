import java.util.Scanner;

public class Program {
    private static final int WEEKS_IN_SEPTEMBER = 5;

    private static final int MAX_CLASSES = 10;

    private static final int MAX_STUDENTS = 10;

    private static final int MAX_DAY = 30;

    private static final int MAX_HOUR = 6;

    private static final int PAIR_OF_VALS = 2;

    private static final int TIME = 0;

    private static final int DAY = 1;

    private static final int ATT = 1;

    private static final char HERE = 1;

    private static final char NOT_HERE = 2;

    public static char weekdayToChar(String weekday) {
        switch (weekday) {
            case "MO":
                return 0;
            case "TU":
                return 1;
            case "WE":
                return 2;
            case "TH":
                return 3;
            case "FR":
                return 4;
            case "SA":
                return 5;
            case "SU":
                return 6;
            default:
                return 0;
        }
    }

    public static String charToWeekday(char ch) {
        switch (ch) {
            case 0:
                return "MO";
            case 1:
                return "TU";
            case 2:
                return "WE";
            case 3:
                return "TH";
            case 4:
                return "FR";
            case 5:
                return "SA";
            case 6:
                return "SU";
            default:
                return "";
        }
    }

    public static int readNames(Scanner s, String[] arr) {
        int nameCount = 0;

        for (int i = 0; i < MAX_STUDENTS; ++i) {
            String name = s.next();

            if (name.equals(".")) {
                break;
            }

            arr[i] = name;
            nameCount += 1;
        }

        return nameCount;
    }

    public static int readClasses(Scanner s, char[][] arr) {
        int classSize = 0;

        for (int i = 0; i < MAX_CLASSES; ++i) {
            String time = s.next();

            String weekday = "";

            char timeCh = time.toCharArray()[0];

            if (time.equals(".")) {
                break;
            }

            weekday = s.next();

            arr[i][TIME] = timeCh;
            arr[i][DAY] = weekdayToChar(weekday);
            classSize += 1;
        }

        return classSize;
    }

    public static int getNamePosition(String name, String[] arr, int size) {
        for (int i = 0; i < size; ++i) {
            if (name.equals(arr[i])) {
                return i;
            }
        }
        return 0;
    }

    public static void bubbleSortClasses(char[][] arr, int classSize) {
        for (int i = 0; i < classSize; ++i) {
            for (int j = 1; j < classSize - i; ++j) {
                if ((arr[j - 1][DAY] > arr[j][DAY])
                        || (arr[j - 1][DAY] == arr[j][DAY])
                        && (arr[j - 1][TIME] > arr[j][TIME])) {
                    char[] tmp = arr[j - 1];

                    arr[j - 1] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String[] nameArr = new String[MAX_STUDENTS];

        char[][] classArr = new char[MAX_CLASSES][PAIR_OF_VALS];

        char[][][] attendanceArr = new char[MAX_STUDENTS][MAX_DAY][MAX_HOUR];

        int nameCount = readNames(s, nameArr);

        int classCount = readClasses(s, classArr);

        int maxAttendance = nameCount * classCount * WEEKS_IN_SEPTEMBER;

        bubbleSortClasses(classArr, classCount);

        for (int i = 0; i < maxAttendance; ++i) {
            String name = s.next();

            char time = 0;

            char date = 0;

            String attendance = "";

            int namePos = -1;

            if (name.equals(".")) {
                break;
            }

            time = (char) s.nextInt();
            date = (char) s.nextInt();
            attendance = s.next();

            namePos = getNamePosition(name, nameArr, nameCount);
            if (attendance.equals("HERE")) {
                attendanceArr[namePos][date - 1][time] = HERE;
            } else {
                attendanceArr[namePos][date - 1][time] = NOT_HERE;
            }
        }


        System.out.printf("%10s", "");
        for (int day = 1; day <= MAX_DAY; ++day) {
            for (int i = 0; i < classCount; ++i) {
                char time = classArr[i][TIME];

                String weekday = charToWeekday(classArr[i][DAY]);

                int weekdayNum = (int) classArr[i][DAY];

                if ((day % 7) == weekdayNum) {
                    System.out.printf("%c:00 %2s %2d|", time, weekday, day);
                }
            }
        }

        System.out.println();

        for (int i = 0; i < nameCount; ++i) {
            System.out.printf("%10s", nameArr[i]);
            for (int day = 1; day <= MAX_DAY; ++day) {
                for (int j = 0; j < classCount; ++j) {
                    int time = (int) (classArr[j][TIME] - '0');

                    String weekday = charToWeekday(classArr[j][DAY]);

                    int weekdayNum = (int) classArr[j][DAY];

                    if ((day % 7) == weekdayNum) {
                        if (attendanceArr[i][day - 1][time] != 0) {
                            if (attendanceArr[i][day - 1][time] == HERE) {
                                System.out.printf("%10d|", 1);
                            } else {
                                System.out.printf("%10d|", -1);
                            }
                        } else {
                            System.out.printf("%10s|", "");
                        }
                    }
                }
            }
            System.out.println();
        }
    }
}