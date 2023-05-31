public class Game implements IGame {
    private static int SIZE = 0;
    public static final String FILLER = " ";
    private static String[][] board;
    private static Player[] players =
            {new Player("Computer", "X"), new Player("Компьютер", "O")};
    private static Player currPlayer = null;

    public Game() {}

    public String getGame(String player, int grid)
   {
       if (grid<=3)
       this.SIZE = grid < 3 ? 3 : grid;
       else this.SIZE=grid > 5 ? 5 : grid;
       this.board = new String[SIZE][SIZE];
       this.players[0] = new Player(player, "X");
       currPlayer = players[0];
       System.out.println("fff");
       for (int i = 0; i <  this.SIZE; i++) {
           for (int j = 0; j <  this.SIZE; j++) {
               this.board[i][j] = FILLER;
           }
       }
       return "Ваш ход!";
    }

/*    public void move(int xPosition, int yPosition) { //String
        if (!isValidMove(xPosition, yPosition))
            return;
        board[xPosition][yPosition] = currPlayer.getSymbol();
        changePlayer();
        System.out.println("now player is... " +currPlayer.getName());
        printBoard();
        if (!isGameOver()&&getName().equals("Computer2")) {
            currPlayer.playAIMove(this);
        }
    }*/
    public Integer[] move(int xPosition, int yPosition) { //
        Integer[] moves={-1, -1};
        if (!isValidMove(xPosition, yPosition))
            return moves;
        else {
        board[xPosition][yPosition] = currPlayer.getSymbol();
        changePlayer();
            if (!isGameOver()&&getName().equals("Компьютер")) {
                moves = currPlayer.playAIMove(this);
                board[moves[0]][moves[1]] = currPlayer.getSymbol();
                System.out.println("[ход" + moves[0] + moves[1]);
                changePlayer();
            }
       }
        return moves;
    }

   public String getName() {
         return currPlayer.getName();
    }

   public boolean isValidMove(int xPosition, int yPosition) {
        if (xPosition >= SIZE || yPosition >= SIZE || xPosition < 0 || yPosition < 0)
            return false;
        if (!board[xPosition][yPosition].equals(FILLER))
            return false;
        return true;
    }

    public void changePlayer() {
        currPlayer = currPlayer.equals(players[1]) ? players[0] : players[1];
    }

    public boolean isGameOver() {
        return getWinner() != null || isNoMovesLeft();
    }

    public boolean isNoMovesLeft() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].equals(FILLER)) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getWinner() {
        String rowSymbol = rowCrossed();
        String columnSymbol = columnCrossed();
        String diagonalSymbol = diagonalCrossed();
        for (Player player : players) {
            if (player.getSymbol().equals(rowSymbol)) return getWinnerName(player);
            if (player.getSymbol().equals(columnSymbol)) return getWinnerName(player);
            if (player.getSymbol().equals(diagonalSymbol)) return getWinnerName(player);
        }
        return null;
    }
    public String getWinnerName(Player player){
        return player.getName();
    }
    public String rowCrossed() {
        for (int i = 0; i < SIZE; i++) {
            String check = board[i][0];
            for (int j = 1; j < SIZE; j++) {
                if (!check.equals(board[i][j])) {
                    check = FILLER;
                    break;
                }
            }
            if (!check.equals(FILLER)) {
                return check;
            }
        }
        return FILLER;
    }

    public String columnCrossed() {
        for (int i = 0; i < SIZE; i++) {
            String check = board[0][i];
            for (int j = 1; j < SIZE; j++) {
                if (!check.equals(board[j][i])) {
                    check = FILLER;
                    break;
                }
            }
            if (!check.equals(FILLER)) {
                return check;
            }
        }
        return FILLER;
    }

    public String diagonalCrossed() {
        String check = board[0][0];
        for (int i = 1; i < SIZE; i++) {
            if (!check.equals(board[i][i])) {
                check = FILLER;
                break;
            }
        }
        if (!check.equals(FILLER)) {
            return check;
        }
        check = board[0][2];
        for (int i = 1; i < SIZE; i++) {
            if (!check.equals(board[i][SIZE - 1 - i])) {
                check = FILLER;
                break;
            }
        }
        if (!check.equals(FILLER)) {
            return check;
        }
        return FILLER;
    }
    public String[][] getBoard() {
        return board;
    }
}
