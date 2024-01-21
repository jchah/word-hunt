public class Letter {
    private final int x;
    private final int y;
    private final char letter;
    private boolean used;

    // Constructor
    public Letter(int x, int y, char letter) {
        this.x = x;
        this.y = y;
        this.letter = letter;
    }

    // Getter methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getLetter() {
        return letter;
    }

    public void free() {
        used = false;
    }

    public void use() {
        used = true;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isAdjacentTo(Letter l2) {
        return (Math.abs(l2.getX() - this.x) <= 1) && (Math.abs(l2.getY() - this.y) <= 1) && !(l2.getX() == this.x && l2.getY() == this.y);
    }
}
