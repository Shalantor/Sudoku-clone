import java.util.*;

public class SudokuSolver{

    private int[][] solution = new int[9][9];

    public SudokuSolver(String toSolve){

        int x = 0;
        int y = 0;
        for(int i = 0; i < 81 ; i++){
            if(toSolve.charAt(i) != '.'){   //check for valid entry in sudoku field
                solution[x][y] = Integer.parseInt(toSolve.substring(i,i+1));
            }
            if( ++y == 9){
                y = 0;
                x++;
            }
        }
        for(int i =0; i < 9; i++){
            for(int j =0; j < 9; j++){
                System.out.print(solution[i][j]);
            }
            System.out.println("");
        }
    }

}
