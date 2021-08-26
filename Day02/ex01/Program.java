import java.util.*;
import java.io.*;
import java.util.stream.*;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: Program inputA.txt inputB.txt");
            return;
        }

        SimilarityComparator comp = new SimilarityComparator();

        if (!comp.readFirstFile(args[0])) {
            return;
        }
        if (!comp.readSecondFile(args[1])) {
            return;
        }

        comp.populateDictionary();

        if (!comp.saveDictionary()) {
            return;
        }

        System.out.printf("Similarity = %.2f\n", comp.computeSimilarity());
    }
}