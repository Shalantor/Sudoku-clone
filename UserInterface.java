import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class UserInterface{

    public JLabel[] tempGameGrid;
    public JLabel[] gameGrid= new JLabel[81];
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
        /*difficulty = easy*/
        JMenuItem easy = new JMenuItem("Easy");
        newGame.add(easy);
        easy.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){
                try{
                    UserInterface.this.setUpGame("easy");
                }
                catch(IOException ex){
                    System.out.println("Gamo ton xristo sou me ta exception");
                }
            }

        });

        /*for intermediate difficulty*/
        JMenuItem intermediate = new JMenuItem("Intermediate");
        newGame.add(intermediate);
        intermediate.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

                try{
                    UserInterface.this.setUpGame("intermediate");
                }
                catch(IOException ex){
                    System.out.println("Gamo ton xristo sou me ta exception");
                }
            }

        });

        /*For expert difficulty*/
        JMenuItem expert = new JMenuItem("Expert");
        newGame.add(expert);
        expert.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

                try{
                    UserInterface.this.setUpGame("easy");
                }
                catch(IOException ex){
                    System.out.println("Gamo ton xristo sou me ta exception");
                }
            }

        });

        menu.setVisible(true);

        /*Creating empty label at top, which will be used for end winning message*/
        JLabel topLabel = new JLabel();
        sudoku.add(topLabel,BorderLayout.NORTH);
        topLabel.setPreferredSize(new Dimension(width,height/6));
        topLabel.setBackground(new Color(223,223,223));
        topLabel.setOpaque(true);
        topLabel.setVisible(true);

        /*Creating game Grid , which will be in center*/
        JLabel center = new JLabel();
        sudoku.add(center,BorderLayout.CENTER);
        center.setLayout(new GridLayout(3,3,2,2));
        center.setPreferredSize(new Dimension(width,height/2));
        center.setBackground(new Color(223,223,223));
        center.setOpaque(true);

        /*Create main grid of game map*/
        tempGameGrid = Utilities.createBigGrid(center);

        /*Create smaller grids*/
        int j = 0 ;
        for(int i =0; j < GRID; i += 9){
            gameGrid =  Utilities.createSmallGrid(tempGameGrid[j],gameGrid,i);
            j++;
        }

        center.validate();

        /*Creating label at bottom, which will be used fot the buttons*/
        JLabel bottomLabel = new JLabel();
        sudoku.add(bottomLabel,BorderLayout.SOUTH);
        bottomLabel.setPreferredSize(new Dimension(width,height/6));

        /*set background color of bottomLabel*/
        bottomLabel.setBackground(new Color(223,223,223));
        bottomLabel.setOpaque(true);

        /*Add components*/
        bottomLabel.setLayout(new FlowLayout(FlowLayout.CENTER));
        Utilities.createPlayButtons(bottomLabel);
        bottomLabel.setVisible(true);

        /*Adjust window size*/
        sudoku.pack();


        sudoku.setVisible(true);
    }

    public void setUpGame(String difficulty) throws IOException{

        /*Used to store numbers*/
        String line ;
        URL link = null;
        BufferedReader in = null;

        /*getting url and opening stream to read from it*/
        try{
            link = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=" + difficulty);
        }
        catch(MalformedURLException ex){
            System.out.println("Malformed url");
            System.exit(0);
        }

        try{
            in = new BufferedReader(new InputStreamReader(link.openStream()));
        }
        catch(IOException ex){
            System.out.println("IOException when trying to open stream");
            System.exit(0);
        }

        /*position = where to place character
        row = which row we are , so we can adjust position(THIS IS NOT THE OVERALL ROW!!!)
        its just the row in the grid we are currently in
        column = which column we are so we can adjust position
        adjRow = used to adjust position when we switch between 2 big grids
        the reason i filled the game map this way was because we are  using gridlayout,
        there was no way to put the labels in an array where we could iterate over it like a
        normal two dimensional array*/
        int position = 0;
        int row = 0;
        int column = 0;
        int adjRow = 0;
        while( (line = in.readLine() ) != null){

            if(row < 3){                            //still in same grid
                position =  row * 3 + adjRow;       //instantiate position(far left side)
                row++;                              //increment row for next iteration
            }
            else if(row == 3){                      //grid is completed go to next grid
                adjRow += 27;                       //adjust starting position
                row = 0;                            //row in  big grid
            }

            //TODO:check if text can be centered with a better method than adding spaces*/

            for(int i = 0; i < line.length(); i++){
                gameGrid[position].setText("      " + line.charAt(i));
                column ++ ;
                if(column == 3){                        //we will get to next big grid on the right side
                    position += 7;                      //jump to next Big grid (right side)
                    column = 0;
                }
                else{                                   //else if in same grid, just increment position
                    position++;
                }
            }


        }

        /*Close stream*/
        try{
            in.close();
        }
        catch(IOException ex){
            System.out.println("IOException when closing stream");
            System.exit(0);
        }

    }



}
