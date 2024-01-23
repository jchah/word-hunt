import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private boolean isDragging = false;

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

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(scoreLabel, BorderLayout.NORTH);

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
        frame.setVisible(true);
    }


    // TODO shouldn't be able to use non-adjacent letters to form words (which are legal because they can also be formed in an adjacent way)

    private void confirmWord() {
        String input = currentWord.toString();
        System.out.println(input);
        System.out.println(usedWords.contains(input));
        if (!usedWords.contains(input)) {
            boolean legalSequence = board.isLegalSequence(input);
            boolean validWord = dictionary.isValidWord(input);

            int wordLength = input.length();
            if (legalSequence && validWord && wordLength >= 3) {
                score += wordLength == 3 ? 100 : (wordLength - 3) * 400;
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

        letterButton.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    Point mousePoint = SwingUtilities.convertPoint(letterButton, e.getPoint(), letterButton.getParent());
                    Component c = letterButton.getParent().getComponentAt(mousePoint);
                    if (c instanceof JButton targetButton) {
                        Rectangle bounds = targetButton.getBounds();
                        Point center = new Point(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
                        double distance = center.distance(mousePoint);

                        // Only select the button if the cursor is within half the width/height of the button (closer to the center)
                        if (distance < Math.min(bounds.width, bounds.height) / 2.0) {
                            handleSelection(targetButton);
                        }
                    }
                }
            }
        });

        letterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDragging = true;
                handleSelection(letterButton);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
                confirmWord();
            }
        });

        return letterButton;
    }

    private void handleSelection(JButton button) {
        if (!selectedButtons.contains(button)) {
            selectedButtons.add(button);
            button.setBackground(new Color(144, 238, 144)); // Light green
            currentWord.append(button.getText());
        }
    }
}
