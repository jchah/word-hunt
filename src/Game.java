import java.util.Scanner;

public class Game {
    private final Board board;
    private final Scanner scanner;
    private static final Dictionary dictionary = new Dictionary("words.txt");
    private int totalPoints;

    public Game(int rows, int cols) {
        totalPoints = 0;
        board = new Board(rows, cols);
        scanner = new Scanner(System.in);
    }

    public void start() {
        int pointsAwarded = 0;
        displayBoard();
        while (true) {
            System.out.println("Enter a word to check (or 'exit' to quit): ");
            String input = scanner.nextLine().toUpperCase().strip();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            boolean legalSequence = board.isLegalSequence(input);
            System.out.println("Sequence " + (legalSequence ? "found: " + input : "not found."));

            boolean validWord = dictionary.isValidWord(input);
            System.out.println(input + " is" + (validWord ? "" : " not") + " a valid word");

            if (legalSequence && validWord) {
                int wordLength = input.length();
                pointsAwarded = wordLength >= 3 ? (wordLength == 3 ? 100 : (wordLength - 3) * 400) : 0;
            }

            totalPoints += pointsAwarded;
            System.out.println(pointsAwarded + " points awarded");
            pointsAwarded = 0;
        }
    }

    private void displayBoard() {
        Letter[][] grid = board.getBoard();
        for (Letter[] row : grid) {
            for (Letter letter : row) {
                System.out.print(letter.getLetter() + "    ");
            }
            System.out.println();
        }
    }
}