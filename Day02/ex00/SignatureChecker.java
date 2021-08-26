import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class SignatureChecker {
    private Map<String, int[]> signatureMap;
    private FileOutputStream resultFile;

    public SignatureChecker() {
        signatureMap = new HashMap<>();
        resultFile = null;
    }

    public boolean init() {
        if (!loadSignatures()) {
            return false;
        }

        try {
            resultFile = new FileOutputStream("result.txt");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean loadSignatures() {
        try (FileInputStream sigFile = new FileInputStream("signatures.txt")) {
            int c;
            String s = "";
            do {
                c = sigFile.read();
                if (c == '\n' || c == -1) {
                    if (!processSignature(s))
                         return false;
                    s = "";
                } else {
                    s += (char) c;
                }
            } while (c != -1);
        } catch (FileNotFoundException e) {
            System.err.println("Error: signatures.txt not found");
            return false;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            System.err.printf("Error parsing file signature: %s\n", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean processSignature(String s) {
        String split[] = s.split(",");
        if (split.length != 2) {
            System.err.println("Format error in signatures.txt");
            return false;
        }

        String[] byteSplit = split[1].trim().split("\\s+");
        if (byteSplit.length == 0) {
            System.err.printf("%s signature is empty\n", split[0]);
            return false;
        }

        int[] sig = new int[byteSplit.length];
        for (int i = 0; i < byteSplit.length; ++i) {
            sig[i] = Integer.parseInt(byteSplit[i], 16);
        }
        signatureMap.put(split[0], sig);

        return true;
    }

    private void writeResult(String ext) {
        if (resultFile == null) {
            return;
        }
        try {
            resultFile.write(ext.getBytes());
            resultFile.write('\n');
        } catch (IOException e) {}
    }

    public void checkFileSignature(String path) {
        List<Integer> byteLst = new ArrayList<>();
        List<Map.Entry<String, int[]>> curSearchLst = null;
        List<Map.Entry<String, int[]>> prevSearchLst = null;

        try (FileInputStream checkFile = new FileInputStream(path)) {
            int c;
            int p = 0;
            while ((c = checkFile.read()) != -1) {
                byteLst.add(c);

                int[] byteArr = byteLst.stream().mapToInt(i -> i).toArray();

                curSearchLst = signatureMap.entrySet().stream()
                               .filter(item -> partialSignatureEquals(item.getValue(), byteArr))
                               .collect(Collectors.toList());
                if (curSearchLst.size() == 0) {
                    byteLst.remove(byteLst.size() - 1);
                    break;
                }
                prevSearchLst = curSearchLst;
            }
        } catch (Exception e) {
            System.out.println("UNDEFINED");
            return;
        }

        if (prevSearchLst == null) {
            System.out.println("UNDEFINED");
            return;
        }

        if (prevSearchLst.size() > 1) {
            int[] byteArr = byteLst.stream().mapToInt(i -> i).toArray();
            prevSearchLst = signatureMap.entrySet().stream()
                            .filter(item -> Arrays.equals(item.getValue(), byteArr))
                            .collect(Collectors.toList());
            if (prevSearchLst.size() == 0) {
                System.out.println("UNDEFINED");
                return;
            }
        }

        System.out.println("PROCESSED");
        writeResult(prevSearchLst.get(0).getKey());
    }

    private static boolean partialSignatureEquals(int[] arr1, int[] arr2) {
        if (arr1.length < arr2.length) {
            return false;
        }

        for (int i = 0; i < arr2.length; ++i) {
            if (arr1[i] != arr2[i])
                return false;
        }
        return true;
    }
}