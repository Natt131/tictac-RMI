import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) &&
                Objects.equals(symbol, player.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, symbol);
    }

    private String name;

    private String symbol;

    public  Integer[] playAIMove(Game game) {

        String[][] board = game.getBoard();
        Move bestMove = new Move(-1, -1, Integer.MIN_VALUE);
        int gridSize = board.length;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (board[i][j].equals(Game.FILLER)) {
                    board[i][j] = getSymbol();
                    Move move = new Move(i, j, calculateMoveCost(getSymbol(), board));
                    if (move.getScore() > bestMove.getScore()) {
                        bestMove = move;
                    }
                    board[i][j] = Game.FILLER;
                }
            }
        }
        Integer[] best={bestMove.getRow(), bestMove.getColumn()};
        //game.move(bestMove.getRow(), bestMove.getColumn());
        return best;
    }

    private int calculateMoveCost(final String symbol, String[][] board) {
        int size = board.length;
        List<Scorer> scorerList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Scorer rowScorer = new Scorer();
            Scorer colScorer = new Scorer();
            for (int j = 0; j < size; j++) {
                scoreBasedOnSymbol(symbol, board[i][j], rowScorer);
                scoreBasedOnSymbol(symbol, board[j][i], colScorer);
            }
            scorerList.add(rowScorer);
            scorerList.add(colScorer);
        }

        Scorer diagonal1Scorer = new Scorer();
        Scorer diagonal2Scorer = new Scorer();
        for (int i = 0; i < size; i++) {
            scoreBasedOnSymbol(symbol, board[i][i], diagonal1Scorer);
            scoreBasedOnSymbol(symbol, board[i][size - i - 1], diagonal2Scorer);
        }
        scorerList.add(diagonal1Scorer);
        scorerList.add(diagonal2Scorer);

        int score = 0;
        for (Scorer scorer : scorerList) {
            score += scorer.getScore(size);
        }

        return score;
    }

    private void scoreBasedOnSymbol(String symbol, String symbolToCompare, Scorer scorer) {
        if (symbol.equals(symbolToCompare))
            scorer.addWin();
        else if (Game.FILLER.equals(symbolToCompare))
            scorer.addTie();
        else
            scorer.addLoss();
    }
}
