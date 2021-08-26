import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Program {
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
        
        System.out.println(curPath.toString());

        Scanner s = new Scanner(System.in);

        while (true) {
            String input = s.nextLine();

            if (input.equals("exit")) {
                return;
            }

            if (input.equals("ls")) {
                for (File f : curPath.toFile().listFiles()) {
                    try {
                        System.out.printf("%s %d B\n", f.getName(), Files.size(f.toPath()));
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }
    }
}