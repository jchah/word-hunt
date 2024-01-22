import java.io.*;
import java.util.HashSet;

public class Dictionary {
    private final HashSet<String> words;

    public Dictionary(String fileName) {
        words = new HashSet<>();
        loadWords(fileName);
    }

    private void loadWords(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public boolean isValidWord(String word) {
        return words.contains(word.toLowerCase());
    }
}
