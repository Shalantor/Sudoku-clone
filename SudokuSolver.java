import java.util.*;

public class SudokuSolver extends Thread{

    public int[][] solution = new int[9][9];
    public boolean finished = false;                //signal to thread waiting that solution is ready

    public SudokuSolver(String toSolve){
        int x = 0;
        int y = 0;
        for(int i = 0; i < 81 ; i++){
            if(toSolve.charAt(i) != '.'){   //check for valid entry in sudoku field
                solution[x][y] = Integer.parseInt(toSolve.substring(i,i+1));
            }
            if( ++y == 9){                  //out of bounds
                y = 0;
                x++;
            }
        }
    }

    @Override
    public void run(){
        this.solve(0,0,solution);
        finished = true;
    }

    public boolean solve(int x,int y,int matrix[][]){
        if( x == 9){    //out of bounds
            x = 0;
            if ( ++ y == 9){    //finished
                return true;
            }
        }
        if( matrix[x][y] != 0){ //check for fields that are preset
            return solve(x+1,y,matrix);
        }
        for( int num = 1; num <= 9; num++){             //solve recursively
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
        int rowOffset = ( x / 3) * 3;       //same box
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
