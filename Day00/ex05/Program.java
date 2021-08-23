import java.util.Scanner;

public class Program {
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

    public static int getNamePosition(String name, String[] arr, int size) {
        for (int i = 0; i < size; ++i) {
            if (name.equals(arr[i])) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        String[] studentsArr = new String[10];
        char[][] classArr = new char[10][2];
        Scanner s = new Scanner(System.in);

        int nameCount = 0;
        int classSize = 0;
        int dayCount = 0;

        for (int i = 0; i < 10; ++i) {
            String name = s.next();
            if (name.equals(".")) {
                break;
            }
            studentsArr[i] = name;
            nameCount += 1;
        }

        for (int i = 0; i < 10; ++i) {
            String time = s.next();
            if (time.equals(".")) {
                break;
            }
            char timeCh = time.toCharArray()[0];
            classArr[i][0] = timeCh;

            String weekday = s.next();
            classArr[i][1] = weekdayToChar(weekday);
            classSize += 1;
        }

        for (int i = 0; i < classSize; ++i) {
            for (int j = 1; j < classSize - i; ++j) {
                if ((classArr[j - 1][1] > classArr[j][1])
                        || (classArr[j - 1][1] == classArr[j][1]) && (classArr[j - 1][0] > classArr[j][0])) {
                    char[] tmp = classArr[j - 1];
                    classArr[j - 1] = classArr[j];
                    classArr[j] = tmp;
                }
            }
        }


        for (int day = 1; day <= 30; ++day) {
            for (int i = 0; i < classSize; ++i) {
                int weekdayNum = (int)classArr[i][1];
                if ((day % 7) == weekdayNum) {
                    dayCount += 1;
                }
            }
        }

        char[][][] attendanceArr = new char[nameCount][30][2];
        for (int i = 0; i < dayCount; ++i) {
            String name = s.next();
            if (name.equals(".")) {
                break;
            }
            char time = (char)s.nextInt();
            char date = (char)s.nextInt();
            String attendance = s.next();
            char isPresent = (char)(attendance.equals("HERE") ? 1 : 2);
            int namePos = getNamePosition(name, studentsArr, nameCount);
            attendanceArr[namePos][date - 1][0] = time;
            attendanceArr[namePos][date - 1][1] = isPresent;
        }

        for (int namePos = 0; namePos < nameCount; ++namePos) {
            for (int i = 0; i < dayCount; ++i) {
                for (int j = 1; j < dayCount - i; ++j) {
                    if ((attendanceArr[namePos][j - 1][1] > attendanceArr[namePos][j][1])
                            || (attendanceArr[namePos][j - 1][1] == attendanceArr[namePos][j][1]) && (attendanceArr[namePos][j - 1][0] > attendanceArr[namePos][j][0])) {
                        char[] tmp = attendanceArr[namePos][j - 1];
                        attendanceArr[namePos][j - 1] = attendanceArr[namePos][j];
                        attendanceArr[namePos][j] = tmp;
                    }
                }
            }
        }


        System.out.printf("%10s", "");
        for (int day = 1; day <= 30; ++day) {
            for (int i = 0; i < classSize; ++i) {
                char time = classArr[i][0];
                String weekday = charToWeekday(classArr[i][1]);
                int weekdayNum = (int)classArr[i][1];
                if ((day % 7) == weekdayNum) {
                    System.out.printf("%c:00 %2s %2d|", time, weekday, day);
                }
            }
        }
        System.out.println();
        for (int i = 0; i < nameCount; ++i) {
            System.out.printf("%10s", studentsArr[i]);
            for (int day = 1; day <= 30; ++day) {
                for (int j = 0; j < classSize; ++j) {
                    String weekday = charToWeekday(classArr[j][1]);
                    int weekdayNum = (int)classArr[j][1];
                    if ((day % 7) == weekdayNum) {
                        if (attendanceArr[i][day - 1][1] != 0) {
                            int isPresent = (attendanceArr[i][day - 1][1] == 1 ? 1 : -1);
                            System.out.printf("%10d|", isPresent);
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