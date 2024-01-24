import java.io.*;
import java.util.HashSet;

public class Dictionary {
    private static final HashSet<String> words = new HashSet<>();

    static {
        loadWords();
    }

    private static void loadWords() {
        try (BufferedReader br = new BufferedReader(new FileReader("words.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static boolean isValidWord(String word) {
        return words.contains(word.toLowerCase());
    }
}
