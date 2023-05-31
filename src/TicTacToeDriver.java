import javax.swing.JFrame;
public class TicTacToeDriver {

    public static void main(String[] args) {

        JFrame frame = new TicTacToe();
        frame.setTitle("Крестики-Нолики");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}