import java.util.*;
import java.io.*;
import java.util.stream.*;

class SimilarityComparator {
    private Map<String, Integer> wordMap1;
    private Map<String, Integer> wordMap2;
    private Set<String> dictionary;

    public SimilarityComparator() {
        this.wordMap1 = new HashMap<>();
        this.wordMap2 = new HashMap<>();
        this.dictionary = new TreeSet<>();
    }

    public boolean readFirstFile(String filename) {
        return readFile(filename, this.wordMap1);
    }

    public boolean readSecondFile(String filename) {
        return readFile(filename, this.wordMap2);
    }

    private static boolean readFile(String filename, Map<String, Integer> map) {
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
            System.err.printf("Error reading '%s'\n", filename);
            return false;
        }
        return true;
    }

    public void populateDictionary() {
        this.dictionary.addAll(this.wordMap1.keySet());
        this.dictionary.addAll(this.wordMap2.keySet());
    }

    public boolean saveDictionary() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("dictionary.txt"))) {
            for (String w : this.dictionary) {
                out.write(w);
                out.newLine();
            }
        } catch (IOException x) {
            System.err.println("Error writing words to 'dictionary.txt'");
            return false;
        }
        return true;
    }

    public double computeSimilarity() {
        List<Integer> vec1 = this.dictionary.stream()
                                .map(word -> this.wordMap1.getOrDefault(word, 0))
                                .collect(Collectors.toList());
        List<Integer> vec2 = this.dictionary.stream()
                                .map(word -> this.wordMap2.getOrDefault(word, 0))
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

        if (dictionary.size() == 0) {
            return 1;
        }
        if (numenator == 0) {
            return 0;
        }
        
        return (numenator / (sum1 * sum2));
    }
}