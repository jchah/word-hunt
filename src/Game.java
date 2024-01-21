import java.util.Scanner;

public class Game {
    private final Board board;
    private final Scanner scanner;

    public Game(int rows, int cols) {
        board = new Board(rows, cols);
        scanner = new Scanner(System.in);
    }

    public void start() {
        displayBoard();
        while (true) {
            System.out.println("Enter a word to check (or 'exit' to quit): ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            boolean result = board.isLegalSequence(input);
            System.out.println("Sequence " + (result ? "found: " + input : "not found."));
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