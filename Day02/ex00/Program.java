import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        Map<String, int[]> map = new HashMap<>();

        try (FileInputStream sigFile = new FileInputStream("signatures.txt")) {
            int c;
            String s = "";
            do {
                c = sigFile.read();
                if (c == '\n' || c == -1) {
                    String split[] = s.split(",");
                    if (split.length != 2) {
                        System.err.println("Format error in signatures.txt");
                        return;
                    }
                    String[] byteSplit = split[1].trim().split("\\s+");
                    if (byteSplit.length == 0) {
                        System.err.printf("%s signature is empty\n", split[0]);
                    }
                    int[] sig = new int[byteSplit.length];
                    for (int i = 0; i < byteSplit.length; ++i) {
                        sig[i] = Integer.parseInt(byteSplit[i], 16);
                    }
                    map.put(split[0], sig);
                    s = "";
                } else {
                    s += (char) c;
                }
            } while (c != -1);
        } catch (FileNotFoundException e) {
            System.err.println("Error: signatures.txt not found");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.printf("Error parsing file signature: %s\n", e.getMessage());
        }

        Scanner s = new Scanner(System.in);
        String fullPath = s.next();

        List<Integer> byteLst = new ArrayList<>();
        System.out.println(fullPath);
        try (FileInputStream checkFile = new FileInputStream(fullPath)) {
            int c;
            int p = 0;
            while ((c = checkFile.read()) != -1) {
                byteLst.add(c);

                int[] byteArr = byteLst.stream().mapToInt(i -> i).toArray();
                List<Map.Entry<String, int[]>> result = map.entrySet().stream()
                        .filter(item -> Arrays.equals(item.getValue(), byteArr))
                        .collect(Collectors.toList());
                if (result.size() > 0) {
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}