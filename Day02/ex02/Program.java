import java.util.*;

public class Program {
    public static void main(String[] args) {
        if ((args.length != 1) || (args.length == 1
                && !args[0].startsWith("--current-folder="))) {
            System.out.println("Usage: Program --current-folder=path");
            return;
        }

        FileManager manager = new FileManager();

        String path = args[0].substring("--current-folder=".length());
        if (!manager.setCurrentPath(path)) {
            return;
        }

        Scanner s = new Scanner(System.in);

        System.out.println(manager.getCurrentPath().toString());
        while (true) {
            String input = s.nextLine().trim();

            if (input.equals("exit")) {
                return;
            }

            if (input.equals("ls")) {
                manager.printCurrentDirectory();
                continue;
            } else if (input.startsWith("ls ")) {
                System.err.println("ls: does not support any arguments");
                continue;
            }

            if (input.equals("cd")) {
                System.err.println("cd: no FOLDER_NAME specified");
                continue;
            } else if (input.startsWith("cd ")) {
                manager.changeDirectory(input.substring(3));
                continue;
            }

            if (input.equals("mv")) {
                System.err.println("mv: no WHAT and WHERE specified");
                continue;
            } else if (input.startsWith("mv ")) {
                String cmd = input.substring(3);
                String[] split = cmd.split("\\s+");
                if (split.length == 1) {
                    System.err.println("mv: no WHERE specified");
                    continue;
                } else if (split.length > 2) {
                    System.err.println("mv: too many arguments");
                    continue;
                }

                manager.moveFile(split[0], split[1]);
                continue;
            }

            System.out.println("Unkown command");
        }
    }
}