import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class UserInterface{

    public UserInterface(int width,int height){

        /*Creating window and setting options for it*/

        JFrame sudoku = new JFrame("SudokuGK");
        sudoku.setSize(width,height);
        sudoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sudoku.setVisible(true);
        sudoku.setLayout(new BorderLayout());

        /*creating labels*/

        JLabel title = new JLabel("New_Game");
        title.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.blue));
        sudoku.add(title,BorderLayout.NORTH);

        JLabel gameGrid = new JLabel();
        sudoku.add(gameGrid,BorderLayout.CENTER);
    }

}
