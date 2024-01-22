import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordHuntBoard::new);
        Game game = new Game(4, 4);
        game.start();
    }
}