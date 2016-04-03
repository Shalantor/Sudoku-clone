import javax.swing.JFrame;

public class UserInterface{

    public UserInterface(int width,int height){
        JFrame sudoku = new JFrame("SudokuGK");
        sudoku.setSize(width,height);
        sudoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sudoku.setVisible(true);
    }

}
