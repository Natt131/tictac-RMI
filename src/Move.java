public class Move {
    private int row;
    private int column;
    private int score;
    public int getRow() {
        return row;
    }

/*    @Override
    public String toString() {
        return "Move{" +
                "row=" + row +
                ", column=" + column +
                ", score=" + score +
                '}';
    }*/

    public int getColumn() {
        return column;
    }

    public int getScore() {
        return score;
    }

    public Move(int row, int column, int score) {
        this.row = row;
        this.column = column;
        this.score = score;
    }
}
