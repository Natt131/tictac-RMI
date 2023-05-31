    import java.rmi.Remote;
    import java.rmi.RemoteException;

    public interface IGame extends Remote {
        String getGame(String player, int grid) throws RemoteException;
        Integer[] move(int xPosition, int yPosition) throws RemoteException; //boid was last
        boolean isValidMove(int xPosition, int yPosition)throws RemoteException;
        String getName() throws RemoteException;
        String[][] getBoard() throws RemoteException;
        boolean isGameOver()throws RemoteException;

        String getWinner() throws  RemoteException;
        String getWinnerName(Player player) throws RemoteException;
    }

