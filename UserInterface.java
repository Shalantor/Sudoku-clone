import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class UserInterface{

    public UserInterface(int width,int height){

        /*Creating window and setting options for it*/

        JFrame sudoku = new JFrame("SudokuGK");
        sudoku.setSize(width,height);
        sudoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sudoku.setLayout(new BorderLayout());

        /*graphical components*/

        /*creating menubar*/
        JMenuBar menu = new JMenuBar();
        menu.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.gray));
        sudoku.setJMenuBar(menu);

        /*items of menu*/
        JMenu newGame = new JMenu("New_Game");
        menu.add(newGame);

        /*drop down menu*/
        JMenuItem easy = new JMenuItem("Easy");
        newGame.add(easy);
        JMenuItem intermediate = new JMenuItem("Intermediate");
        newGame.add(intermediate);
        JMenuItem expert = new JMenuItem("Expert");
        newGame.add(expert);

        menu.setVisible(true);

        JLabel gameGrid = new JLabel();
        sudoku.add(gameGrid,BorderLayout.CENTER);

        sudoku.setVisible(true);
    }

}
