public record Letter(int x, int y, char letter) {
    public boolean isAdjacentTo(Letter l2) {
        return (Math.abs(l2.x - this.x) <= 1) && (Math.abs(l2.y - this.y) <= 1) && !(l2.x == this.x && l2.y == this.y);
    }

}
