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
        this.solve(0,0,solution);
        for(int i =0; i < 9; i++ ){
            for(int j =0; j < 9; j++){
                System.out.print(solution[i][j]);
            }
            System.out.println("");
        }
    }

    public boolean solve(int x,int y,int matrix[][]){
        if( x == 9){    //out of bounds
            x = 0;
            if ( ++ y == 9){    //finished
                return true;
            }
        }
        if( matrix[x][y] != 0){ //check for fields that are pre set
            return solve(x+1,y,matrix);
        }
        for( int num = 1; num <= 9; num++){
            if(legalPosition(x,y,num,matrix)) {
                matrix[x][y] = num;
                if( solve(x+1,y,matrix) ){
                    return true;
                }
            }
        }
        matrix[x][y] = 0;       //when backtracking reset to 0
        return false;
    }

    public boolean legalPosition(int x,int y,int num,int[][] matrix){
        for(int i = 0; i < 9; i++){     //same row
            if( num == matrix [i][y]){
                return false;
            }
        }
        for(int i =0; i < 9; i++){      //same column
            if( num == matrix[x][i]){
                return false;
            }
        }
        int rowOffset = ( x / 3) * 3;
        int columnOffset = ( y / 3) * 3;
        for( int i = 0; i < 3; i++){
            for (int j =0; j < 3; j++){
                if( num == matrix[rowOffset + i][columnOffset + j]){
                    return false;
                }
            }
        }
        //everything is ok if we reach this point
        return true;
    }




}
