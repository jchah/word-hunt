import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final int rows;
    private final int cols;
    private final Letter[][] board;
    private final Map<Character, Integer> letterWeights;
    private final ArrayList<Character> weightedLetters = new ArrayList<>();

    public Board(int GRID_SIZE) {
        this.rows = GRID_SIZE;
        this.cols = GRID_SIZE;
        this.board = new Letter[rows][cols];

        letterWeights = new HashMap<>();
        int[] frequencies = {9, 2, 3, 4, 13, 2, 2, 6, 7, 1, 1, 4, 3, 7, 8, 2, 1, 6, 6, 9, 3, 1, 2, 1, 3, 1};

        for (int i = 0; i < 26; i++) {
            char ch = (char) ('A' + i);
            letterWeights.put(ch, frequencies[i]);
        }

        createWeightedLetters();
        createBoard();
    }

    private void createWeightedLetters() {
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            int weight = letterWeights.get(ch);
            for (int i = 0; i < weight; i++) {
                weightedLetters.add(ch);
            }
        }
    }

    private char pickLetter() {
        int rand = (int) (Math.random() * weightedLetters.size());
        return weightedLetters.get(rand);
    }

    private void createBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new Letter(j, i, pickLetter());
            }
        }
    }

    public Letter[][] getBoard() {
        return board;
    }

    public boolean isLegalSequence(String sequence) {
        char[] characters = sequence.toCharArray();
        ArrayList<ArrayList<Letter>> letters2D = new ArrayList<>();

        for (char character : characters) {
            ArrayList<Letter> matchingLetters = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (board[i][j].getLetter() == character) {
                        matchingLetters.add(board[i][j]);
                    }
                }
            }
            letters2D.add(new ArrayList<>(matchingLetters));
            matchingLetters.clear();
        }
        boolean result = hasAdjacentSequence(letters2D);

        for(Letter[] row: board) {
            for (Letter col: row) {
                col.free();
            }
        }

        return result;
    }
    private boolean hasAdjacentSequence(ArrayList<ArrayList<Letter>> letters2D) {
        if (letters2D == null || letters2D.isEmpty()) {
            return false;
        }

        // Start with the first list of letters and try to build a sequence
        ArrayList<Letter> startLetters = letters2D.get(0);
        for (Letter startLetter : startLetters) {
            startLetter.use(); // Mark the starting letter as used
            if (buildSequence(startLetter, letters2D, 1)) {
                return true;
            }
            startLetter.free(); // Unmark if not part of a valid sequence
        }

        return false;
    }

    private boolean buildSequence(Letter currentLetter, ArrayList<ArrayList<Letter>> letters2D, int index) {
        if (index >= letters2D.size()) {
            // All letters in the sequence are successfully found
            return true;
        }

        ArrayList<Letter> nextLetters = letters2D.get(index);
        for (Letter nextLetter : nextLetters) {
            if (!nextLetter.isUsed() && currentLetter.isAdjacentTo(nextLetter)) {
                nextLetter.use();
                if (buildSequence(nextLetter, letters2D, index + 1)) {
                    return true;
                }
                nextLetter.free(); // Unmark if not part of a valid sequence
            }
        }
        return false;
    }
}
