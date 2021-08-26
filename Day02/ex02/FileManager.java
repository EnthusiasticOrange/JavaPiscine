import java.io.*;
import java.nio.file.*;

class FileManager {
    enum Size {
        B, KB, MB, GB, TB;

        public boolean hasNext() {
            return ordinal() < (values().length - 1);
        }

        public Size next() {
            return values()[ordinal() + 1];
        }
    }

    private Path currentPath;

    public FileManager() {
        this.currentPath = null;
    }

    public boolean setCurrentPath(String path) {
        this.currentPath = Paths.get(path).toAbsolutePath().normalize();
        if (!this.currentPath.toFile().exists()) {
            System.err.println("Folder does not exist");
            return false;
        }
        if (!this.currentPath.toFile().isDirectory()) {
            System.err.println("Current folder is not a directory");
            return false;
        }

        return true;
    }

    public Path getCurrentPath() {
        return this.currentPath;
    }

    public void printCurrentDirectory() {
        for (File f : this.currentPath.toFile().listFiles()) {
            try {
                Size eSize = Size.B;
                double size = Files.size(f.toPath());
                while (eSize.hasNext()) {
                    double newSize = size / 1024f;
                    if (newSize <= 0.99) {
                        break;
                    }
                    size = newSize;
                    eSize = eSize.next();
                }
                System.out.printf("%s %.2f %s\n", f.getName(), size, eSize);
            } catch (IOException e) {
                System.err.println("ls: Error reading files");
            }
        }
    }

    public void changeDirectory(String pathStr) {
        Path path = this.currentPath.resolve(Paths.get(pathStr)).normalize();
        File pathFile = path.toFile();

        if (!pathFile.exists()) {
            System.err.printf("cd: '%s' does not exists\n", pathStr);
        } else if (!pathFile.isDirectory()) {
            System.err.printf("cd: '%s' is not a directory\n", pathStr);
        } else {
            this.currentPath = path;
            System.out.println(this.currentPath.toString());
        }
    }

    public void moveFile(String from, String to) {
        Path moveFrom = this.currentPath.resolve(Paths.get(from)).normalize();
        File moveFromFile = moveFrom.toFile();

        if (!moveFromFile.exists()) {
            System.err.printf("mv: '%s' does not exists\n", from);
            return;
        } else if (moveFromFile.isDirectory()) {
            System.err.printf("mv: '%s' is a directory\n", from);
            return;
        }

        Path moveTo = this.currentPath.resolve(Paths.get(to)).normalize();
        File moveToFile = moveTo.toFile();

        if (moveToFile.exists() && moveToFile.isFile()) {
            System.err.printf("mv: '%s' already exists\n", to);
            return;
        }

        if (moveToFile.isDirectory()) {
            moveTo = Paths.get(moveTo.toString() + File.separator + moveFromFile.getName());
            moveToFile = moveTo.toFile();
        }
        moveFromFile.renameTo(moveToFile);
    }
}