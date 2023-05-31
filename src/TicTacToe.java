import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import javax.swing.*;
import static java.lang.Integer.parseInt;

public class TicTacToe extends JFrame  {
        static JLabel labelsArray[][];
        static JPanel panel = new JPanel();
        static JPanel board = new JPanel();

        int X = 10, Y = 80;
        int size;
        static JLabel labelInfo;
        IGame stub;

        public TicTacToe() {
                try {
                        Registry registry = LocateRegistry.getRegistry(8080);
                        stub = (IGame) registry.lookup("Game");//типа игры
                } catch (Exception e){
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                }
                panel.setBounds(10, 0, 500, 500);
                panel.setLayout(null);

//                board.setBounds(0, 80, 500, 420);
//                board.setBackground(Color.CYAN);
              //  getContentPane().add(board);
              //  board.setLayout(null);
                //panel.add(board);
                labelInfo = new JLabel("");
                labelInfo.setBounds(5, 350, 250, 30);
                panel.add(labelInfo);


                JButton restart = new JButton("Заново!");
                restart.setBounds(340, 10, 100, 30);
                restart.setEnabled(false);
                panel.add(restart);

                JLabel label = new JLabel("Введите размеры поля (N):");
                JTextField tf = new JTextField();
                label.setBounds(5, 10, 170, 30);
                tf.setBounds(180, 10, 40, 30);
                JButton start = new JButton("Начать!");
                start.setBounds(230, 10, 100, 30);
                panel.add(label);
                panel.add(tf);
                panel.add(start);

                //для хода динамичное добавление
                JLabel labelMove = new JLabel("Координаты хода:");
                JTextField tfMove = new JTextField();
                labelMove.setBounds(5, 40, 140, 30);
                tfMove.setBounds(140, 40, 80, 30);
                JButton Move = new JButton("Вперед!");
                Move.setBounds(230, 40, 100, 30);
                Move.setVisible(true);
                labelMove.setVisible(true);
                tfMove.setVisible(true);
                panel.add(labelMove);
                panel.add(tfMove);
                panel.add(Move);

                restart.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                              X = 10; Y = 80;
                                for (int i = 0; i <size; i++) {
                                        for (int j = 0; j < size; j++) {
                                                panel.remove(labelsArray[i][j]);
                                        }
                                }
                                start.setEnabled(true);
                                Move.setVisible(false);
                                labelMove.setVisible(false);
                                tfMove.setVisible(false);
                                panel.revalidate();
                                panel.repaint();
                        }
                });
                start.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                System.out.println("nfr"+UIManager.getDefaults().get(Move));
                                restart.setEnabled(true);
                                Move.setEnabled(true);
                                labelInfo.setText("");
                              try {   size = parseInt(tf.getText()); }
                              catch(Exception e1){
                                      labelInfo.setText("Размер игрового поля дб задан числом!");

                              }
                              //int
                                if(size<3) getLabels(3) ;
                                else if (size>5) getLabels(5);
                                else getLabels(size);
                                try { //вынести соединение из метода
                                        System.out.println(stub.getGame("Я",size));
                                        Move.setVisible(true);
                                        labelMove.setVisible(true);
                                        tfMove.setVisible(true);
                                       // setupEnterAction(mob);
                                        start.setEnabled(false);
                                }
                                catch (Exception exp) {
                                        System.out.println(exp.getMessage());
                                        exp.printStackTrace();
                                }
                        }
                });

                Move.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                //     tfMove.requestFocus();
                                try{
                                        if (stub.getName().equals("Я")&&!stub.isGameOver()) {
                                                Integer[] coor = getCoordinates(tfMove.getText());
                                                if(labelsArray[coor[0] - 1][coor[1] - 1].getText().equals("")) labelsArray[coor[0] - 1][coor[1] - 1].setText("X");
                                                Integer[] comp = stub.move(coor[0] - 1, coor[1] - 1);
                                                labelsArray[comp[0]][comp[1]].setText("0");
                                        }
                                        if(stub.isGameOver()){
                                          labelInfo.setText(stub.getWinner() != null ? " Победитель " + stub.getWinner() : "Ничья");
                                          Move.setEnabled(false);
                                        }
                                } catch (Exception exp) {}
                                tfMove.requestFocus();
                        }
                });
                setLayout(null);
                add(panel);
        }



        private static Integer[] getCoordinates(String input){
                try {
                        labelInfo.setText("");
                        String[] moves = input.split(",");
                        Integer[] coor ={parseInt(moves[0]), parseInt(moves[1]) };
                        return coor;

                } catch (Exception e) {
                        //   System.out.println("Данные некорректны, попробуйте еще!");
                        labelInfo.setText("Данные некорректны, попробуйте еще!");
                        return null;
                }
        }

        private void getLabels(int size){
                labelsArray = new JLabel[size][size];
               // board=new JPanel();
//                board.setBounds(X, Y, 400, 300);
                for (int i = 0; i <size; i++) {
                        for (int j = 0; j < size; j++) {
                                labelsArray[i][j] = new JLabel();
                                labelsArray[i][j].setBounds(X, Y, 50, 50);
                                labelsArray[i][j].setText("");
                                labelsArray[i][j].setBorder((BorderFactory.createLineBorder(Color.black)));
                                X += 60;
                                if (j == size - 1) {
                                        Y += 60;
                                        X = 10;
                                }
                               // board.add(labelsArray[i][j]);

                                panel.add(labelsArray[i][j]);
                        }
                }
                panel.revalidate(); //panel
                panel.repaint();
        }

        void setupEnterAction(String componentName){
                String keyName = componentName + ".focusInputMap";
                InputMap im = (InputMap) UIManager.getDefaults().get(keyName);
                Object pressedAction = im.get(KeyStroke.getKeyStroke("pressed SPACE"));
                Object releasedAction = im.get(KeyStroke.getKeyStroke("released SPACE"));
                im.put(KeyStroke.getKeyStroke("pressed ENTER"), pressedAction);
                im.put(KeyStroke.getKeyStroke("released ENTER"), releasedAction);
        }

        public void setEnterEvent(){
                setupEnterAction("Вперед!");
                setupEnterAction("RadioButton");
                setupEnterAction("CheckBox");
        }
}
