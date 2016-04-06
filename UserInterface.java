import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;

public class UserInterface{

    public JLabel[] tempGameGrid;
    public JLabel[][] gameGrid= new JLabel[9][9];
    private static final int GRID = 9;

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

        /*Creating empty label at top, which will be used for end winning message*/
        JLabel topLabel = new JLabel();
        sudoku.add(topLabel,BorderLayout.NORTH);
        topLabel.setPreferredSize(new Dimension(width,height/6));
        topLabel.setVisible(true);

        /*Creating label at bottom, which will be used fot the buttons*/
        JLabel bottomLabel = new JLabel();
        sudoku.add(bottomLabel,BorderLayout.SOUTH);
        bottomLabel.setPreferredSize(new Dimension(width,height/6));
        bottomLabel.setVisible(true);

        /*Creating game Grid , which will be in center*/
        JLabel center = new JLabel();
        sudoku.add(center,BorderLayout.CENTER);
        center.setLayout(new GridLayout(3,3,2,2));
        center.setPreferredSize(new Dimension(width,4*height/6));

        tempGameGrid = Utilities.createBigGrid(center);

        for(int i =0; i < GRID;i++){
            gameGrid[i]= (Utilities.createSmallGrid(tempGameGrid[i]));
        }

        center.validate();
        sudoku.pack();


        sudoku.setVisible(true);
    }

}
