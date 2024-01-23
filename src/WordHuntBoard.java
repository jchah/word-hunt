import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.Timer;

public class WordHuntBoard {

    private JPanel boardPanel;
    private JLabel scoreLabel;
    private final StringBuilder currentWord;
    private int score = 0;
    private final int GRID_SIZE;
    private final Board board;
    private static final Dictionary dictionary = new Dictionary("words.txt");
    private final ArrayList<String> usedWords;
    private final ArrayList<JButton> selectedButtons;
//    private boolean isDragging = false;

    public WordHuntBoard(int GRID_SIZE) {
        this.GRID_SIZE = GRID_SIZE;
        board = new Board(GRID_SIZE);
        currentWord = new StringBuilder();
        usedWords = new ArrayList<>();
        selectedButtons = new ArrayList<>();
        initializeGUI();
    }

    private void initializeGUI() {
        JFrame frame = new JFrame("Word Hunt Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        // Score label
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(scoreLabel, BorderLayout.NORTH);

        // Board panel
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE, 10, 10));
        boardPanel.setBackground(new Color(0, 102, 0));

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton letterButton = getjButton(row, col);
                boardPanel.add(letterButton);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);

        JButton confirmButton = getConfirmButton();
        frame.add(confirmButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }


    // TODO shouldn't be able to use non-adjacent letters to form words (which are legal because they can also be formed in an adjacent way)
    private JButton getConfirmButton() {
        JButton confirmButton = new JButton("Confirm Word");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 20));
        confirmButton.addActionListener(e -> {
            String input = currentWord.toString();
            System.out.println(input);
            System.out.println(usedWords.contains(input));
            if (!usedWords.contains(input)) {
                boolean legalSequence = board.isLegalSequence(input);
                boolean validWord = dictionary.isValidWord(input);

                if (legalSequence && validWord) {
                    int wordLength = input.length();
                    score += wordLength >= 3 ? (wordLength == 3 ? 100 : (wordLength - 3) * 400) : 0;
                    scoreLabel.setText("Score: " + score);
                    usedWords.add(input);
                    flashColour(0, 255, 0);
                }
                else {
                    flashColour(255, 0, 0);
                }
            }

            else {
                flashColour(253, 165, 15);
            }
            currentWord.setLength(0); // Reset the word
        });
        return confirmButton;
    }

    private void resetSelectedButtons(){
        for (Component comp : boardPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setBackground(new Color(222, 184, 135));
            }
        }
        selectedButtons.clear();
    }

    private void flashColour(int r, int g, int b) {
        for (JButton button : selectedButtons) {
            button.setBackground(new Color(r, g, b));
        }
        int delay = 250; // 250 ms
        Timer timer = new Timer(delay, e -> resetSelectedButtons());
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
    }

    private JButton getjButton(int row, int col) {
        JButton letterButton = new JButton(Character.toString(board.getBoard()[row][col].getLetter()));
        letterButton.setFocusPainted(false);
        letterButton.setBorderPainted(false);
        letterButton.setFont(new Font("Arial", Font.BOLD, 24));
        letterButton.setBackground(new Color(222, 184, 135));
        letterButton.setOpaque(true);
        letterButton.setForeground(Color.BLACK);

        // Add to current word and toggle button color on click
        letterButton.addActionListener(e -> {
            JButton button = (JButton) e.getSource();
            if (button.getBackground().equals(new Color(144, 238, 144))) { // Light green
                selectedButtons.remove(button);
                button.setBackground(new Color(222, 184, 135)); // Back to original
                // Remove last character from currentWord
                if (!currentWord.isEmpty()) {
                    currentWord.deleteCharAt(currentWord.length() - 1);
                }
            } else {
                button.setBackground(new Color(144, 238, 144)); // Light green
                selectedButtons.add(button);
                currentWord.append(button.getText()); // Add to current word
            }
        });
        return letterButton;
    }
}
