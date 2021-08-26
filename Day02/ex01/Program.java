import java.util.*;
import java.io.*;
import java.util.stream.*;

public class Program {
    public static void readFile(String filename, Map<String, Integer> map) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim().replaceAll("[^a-zA-Zа-яА-Я ]", "").toLowerCase();
                String[] words = line.split("\\s+");
                for (String w : words) {
                    map.merge(w, 1, Integer::sum);
                }
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: Program inputA.txt inputB.txt");
            return;
        }

        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        Set<String> dict = new TreeSet<>();

        readFile(args[0], map1);
        readFile(args[1], map2);

        dict.addAll(map1.keySet());
        dict.addAll(map2.keySet());

        List<Integer> vec1 = dict.stream()
                                .map(word -> map1.getOrDefault(word, 0))
                                .collect(Collectors.toList());
        List<Integer> vec2 = dict.stream()
                                .map(word -> map2.getOrDefault(word, 0))
                                .collect(Collectors.toList());

        int numenator = IntStream.range(0, vec1.size())
                .mapToObj(i -> vec1.get(i) * vec2.get(i))
                .reduce(0, Integer::sum);

        double sum1 = Math.sqrt(vec1.stream()
                .mapToInt(v -> v * v)
                .sum());

        double sum2 = Math.sqrt(vec2.stream()
                .mapToInt(v -> v * v)
                .sum());

        double similarity = (numenator == 0) ? 0 : numenator / (sum1 * sum2);

        System.out.printf("Similarity = %.2f\n", similarity);

        try (BufferedWriter out = new BufferedWriter(new FileWriter("dictionary.txt"))) {
            for (String w : dict) {
                out.write(w);
                out.newLine();
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}