import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Program {
    public enum Size {
        B,
        KB,
        MB,
        GB,
        TB;

        public boolean hasNext() {
            return ordinal() < (values().length - 1);
        }

        public Size next() {
            return values()[ordinal() + 1];
        }
    }

    public static void main(String[] args) {
        if ((args.length != 1) || (args.length == 1
                && !args[0].startsWith("--current-folder="))) {
            System.out.println("Usage: Program --current-folder=path");
            return;
        }
        
        Path curPath = Paths.get(args[0].substring("--current-folder=".length()));
        if (!curPath.toFile().exists()) {
            System.out.println("Folder does not exist");
            return;
        }

        if (!curPath.toFile().isDirectory()) {
            System.out.println("Current folder is not a directory");
            return;
        }

        curPath = curPath.toAbsolutePath().normalize();

//        System.out.println(curPath.toString());

        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println(curPath.toString());
            String input = s.nextLine().trim();

            if (input.equals("exit")) {
                return;
            }

            if (input.equals("ls")) {
                for (File f : curPath.toFile().listFiles()) {
                    try {
                        Size eSize = Size.B;
//                        double size = Files.size(f.toPath());
//                        while (eSize.hasNext()) {
//                            double newSize = size / 1024f;
//                            if (newSize <= 0.99) {
//                                break;
//                            }
//                            size = newSize;
//                            eSize = eSize.next();
//                        }
                        System.out.printf("%s %d %s\n", f.getName(), Files.size(f.toPath()), "B");
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
                continue;
            }

            if (input.equals("cd")) {
                System.out.println("cd: no FOLDER_NAME specified");
                continue;
            } else if (input.startsWith("cd ")) {
                String pathStr = input.substring(3);
                Path path = curPath.resolve(Paths.get(pathStr)).normalize();
                File pathFile = path.toFile();
                if (!pathFile.exists()) {
                    System.err.printf("cd: '%s' does not exists\n", pathStr);
                    continue;
                }
                if (!pathFile.isDirectory()) {
                    System.err.printf("cd: '%s' is not a directory\n", pathStr);
                    continue;
                }
                curPath = path;
                continue;
            }

            if (input.equals("mv")) {
                System.out.println("mv: no WHAT and WHERE specified");
                continue;
            } else if (input.startsWith("mv ")) {
                String cmd = input.substring(3);
                String[] split = cmd.split("\\s+");
                if (split.length == 1) {
                    System.out.println("mv: no WHERE specified");
                    continue;
                } else if (split.length > 2) {
                    System.out.println("mv: too many arguments");
                    continue;
                }

                Path moveFrom = curPath.resolve(Paths.get(split[0])).normalize();
                File moveFromFile = moveFrom.toFile();
                if (!moveFromFile.exists()) {
                    System.err.printf("mv: '%s' does not exists\n", split[0]);
                    continue;
                }
                if (moveFromFile.isDirectory()) {
                    System.err.printf("mv: '%s' is a directory\n", split[0]);
                    continue;
                }

                Path moveTo = curPath.resolve(Paths.get(split[1])).normalize();
                File moveToFile = moveTo.toFile();
                if (moveToFile.exists() && moveToFile.isFile()) {
                    System.err.printf("mv: '%s' already exists\n", split[1]);
                    continue;
                }
                if (moveToFile.isDirectory()) {
                    moveTo = Paths.get(moveTo.toString() + File.separator + moveFromFile.getName());
                    moveToFile = moveTo.toFile();
                }
                
                moveFromFile.renameTo(moveToFile);
            }
        }
    }
}