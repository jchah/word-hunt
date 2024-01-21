import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Board boardObj = new Board(4, 4);
        Letter[][] board = boardObj.getBoard();
        for (Letter[] letters : board) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(letters[j].letter() + "    ");
            }
            System.out.println();
        }
        while(true) {
            String input = in.nextLine();
            System.out.println(boardObj.isLegalSequence(input));
        }
    }
}