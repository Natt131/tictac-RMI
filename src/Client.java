import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    static String  coordinates;
    static Scanner in = new Scanner(System.in);
    static int GRID;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(8080);
            IGame stub = (IGame) registry.lookup("Game");//типа игры
            System.out.print("Введите размеры поля(N): ");
            GRID = in.nextInt();
            System.out.println(stub.getGame("Я",GRID));
          //  stub.printBoard();
            Integer[] coor;
          //  System.out.println("сейчас "+stub.getName());
           while (!stub.isGameOver()){
               if (stub.getName().equals("Я")) {
                   printBoard(stub.getBoard());
                   coor = getCoordinates();
//                   if (stub.isValidMove(coor[0] - 1, coor[1] - 1))
                   stub.move(coor[0] - 1, coor[1] - 1);

//                   }
               }
            }

             stub.getWinner();
            System.out.println(stub.getWinner() != null ? " Победитель " + stub.getWinner() : "Ничья");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
    private static Integer[] getCoordinates(){
        System.out.println("Введите координаты через запятую (сначала строка, потом столбец. пример \"1,3\"");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        try {
        String[] moves = input.split(",");
            //   String[] moves = input.split(",");
            Integer[] coor ={Integer.parseInt(moves[0]),Integer.parseInt(moves[1]) };
            return coor;

        } catch (Exception e) {
            System.out.println("Данные некорректны, попробуйте еще!");
            return getCoordinates();
           // return null;
        }
//        Integer[] coor ={Integer.parseInt(moves[0]),Integer.parseInt(moves[1]) };
//        return coor;
    }

    public static void printBoard(String[][] board) {
        String header = "  ";
        for (int j = 0; j < GRID; j++) {
            header += "|" + (j + 1);
        }
        System.out.println(header);
        for (int j = 0; j < GRID * 3; j++) {
            System.out.print("_");
        }
        System.out.println();
        for (int i = 0; i < GRID; i++) {
            String row = (i + 1) + " ";
            for (int j = 0; j < GRID; j++) {
                row += "|" + board[i][j];
            }
            System.out.println(row);
            for (int j = 0; j < GRID * 3; j++) {
                System.out.print("_");
            }
            System.out.println();
        }
    }

}