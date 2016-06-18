import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class UserInterface{

    /*TODO:remove tempGameGrid*/
    public JLabel[] tempGameGrid;
    private JLabel[][][] isMoveCorrect ;        //First 2d array is for same box, second is for row and columns
    public JLabel[] gameGrid= new JLabel[81];
    private static final int GRID = 9;
    private JLabel activeLabel = null;
    private String linkData;
    private JButton[] buttons;
    private ArrayList<JLabel> sameColors = new ArrayList<JLabel>();//Use for marking labels with same content when selecting
    private ArrayList<JLabel> redColor = new ArrayList<JLabel>();//use when input is not valid for that field

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
                    Utilities.emptyBoard(gameGrid);
                    UserInterface.this.setUpGame("easy");
                }
                catch(IOException ex){
                    System.out.println("IOException occurred");
                    System.exit(0);
                }
            }
        });

        /*for intermediate difficulty*/
        JMenuItem intermediate = new JMenuItem("Intermediate");
        newGame.add(intermediate);
        intermediate.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

                try{
                    Utilities.emptyBoard(gameGrid);
                    UserInterface.this.setUpGame("intermediate");
                }
                catch(IOException ex){
                    System.out.println("IOException occurred");
                    System.exit(0);
                }
            }

        });

        /*For expert difficulty*/
        JMenuItem expert = new JMenuItem("Expert");
        newGame.add(expert);
        expert.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

                try{
                    Utilities.emptyBoard(gameGrid);
                    UserInterface.this.setUpGame("easy");
                }
                catch(IOException ex){
                    System.out.println("IOException occurred");
                    System.exit(0);
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

        /*Add ActionListener to labels*/
        for(JLabel label : gameGrid){
            label.addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e){
                    if(activeLabel == label){           //check if this is the activeLabel
                        return;
                    }
                    for(JLabel red : redColor){
                        if(red.getBackground().getBlue() == 0){
                            red.setBackground(new Color(230,230,230));
                        }
                        else{
                            red.setBackground(new Color(255,255,255));
                        }
                    }
                    redColor.clear();                   //empty list
                    if(label.getBackground().getBlue() == 230){
                        label.setBackground(new Color(240,240,147));
                    }
                    else{
                        label.setBackground(new Color(240,240,148));
                    }
                    if(activeLabel != null ){
                        if(activeLabel.getBackground().getBlue() == 147){       //Color of label is gray
                            activeLabel.setBackground(new Color(230,230,230));
                        }
                        else if(activeLabel.getBackground().getBlue() == 148){
                            activeLabel.setBackground(new Color(255,255,255));  //Color is white
                        }
                    }
                    activeLabel = label;
                    /*Now check for same color labels*/
                    if(!sameColors.isEmpty()){
                        if(activeLabel.getText().charAt(0) != sameColors.get(0).getText().charAt(0)){
                            for(JLabel temp : sameColors){                      //restore colors
                                if(temp.getBackground().getBlue() == 147){       //Color of label is gray
                                    temp.setBackground(new Color(230,230,230));
                                }
                                else if(activeLabel.getBackground().getBlue() == 148){
                                    temp.setBackground(new Color(255,255,255));  //Color is white
                                }
                            }
                            sameColors.clear();                                 //clear list
                        }
                    }
                    char activeChar = activeLabel.getText().charAt(0);
                    if(activeChar == ' '){
                        return;
                    }
                    for(JLabel temp: gameGrid){              //update colors of other labels with same content
                        if(temp.getText().charAt(0) == activeChar){
                            if(temp.getBackground().getBlue() == 230){       //Color of label is gray
                                temp.setBackground(new Color(240,240,147));
                                sameColors.add(temp);
                            }
                            else if (temp.getBackground().getBlue() == 255){
                                temp.setBackground(new Color(240,240,148));  //Color is white
                                sameColors.add(temp);
                            }
                        }
                    }
                }

                public void mouseEntered(MouseEvent e){
                }
                public void mouseReleased(MouseEvent e){
                }
                public void mousePressed(MouseEvent e){
                }
                public void mouseExited(MouseEvent e){
                }
            });
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
        buttons = Utilities.createPlayButtons(bottomLabel);
        bottomLabel.setVisible(true);

        /*Add actionlisteners to number buttons*/
        for(int i = 0; i < 9; i++){
            String buttonText = buttons[i].getText();
            buttons[i].addActionListener( new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    if(activeLabel == null){
                        return;
                    }
                    int activeColor = activeLabel.getBackground().getBlue();
                    if(activeColor == 148){//check for valid field
                        activeLabel.setText(buttonText);
                    }
                    /*Now check labels in same box*/
                    /*First find correct box*/
                    JLabel[] searchBox = null;
                    loop:
                    for( JLabel[] box : isMoveCorrect[0] ){
                        for( JLabel temp : box){
                            if(temp == activeLabel){
                                searchBox = box;
                                break loop;
                            }
                        }
                    }
                    /*Now check for same number in a field*/
                    for(JLabel temp : searchBox){
                        int tempColor = temp.getBackground().getBlue();
                        if( temp!=activeLabel && temp.getText().equals(buttonText)){
                            if(tempColor == 147 || tempColor == 230){           //No change allowed in field number
                                temp.setBackground(new Color(255,0,0));
                            }
                            else if(tempColor == 148 || tempColor == 255){      //Change allowed in field number
                                temp.setBackground(new Color(255,0,1));
                            }
                            redColor.add(temp);
                        }
                    }
                    /*Now check for same row or column*/
                    /*Find row of label*/
                    JLabel[] search = null;
                    int searchIndex = 0;
                    loop:
                    for( JLabel[] row : isMoveCorrect[1]){
                        for( JLabel temp : row){
                            if ( temp == activeLabel){
                                search = row;
                                searchIndex = Arrays.asList(row).indexOf(temp);
                                break loop;
                            }
                        }
                    }
                    /*Now find same in row*/
                    for(JLabel temp : search){
                        int tempColor = temp.getBackground().getBlue();
                        if( temp!= activeLabel && temp.getText().equals(buttonText)){
                            if(tempColor == 147 || tempColor == 230){           //No change allowed in field number
                                temp.setBackground(new Color(255,0,0));
                            }
                            else if(tempColor == 148 || tempColor == 255){      //Change allowed in field number
                                temp.setBackground(new Color(255,0,1));
                            }
                            redColor.add(temp);
                        }
                    }
                    /*Now same column*/
                    for(int i=0; i < 9; i++){
                        JLabel temp = isMoveCorrect[1][i][searchIndex];
                        int tempColor = temp.getBackground().getBlue();
                        if( temp!= activeLabel && temp.getText().equals(buttonText)){
                            if(tempColor == 147 || tempColor == 230){           //No change allowed in field number
                                temp.setBackground(new Color(255,0,0));
                            }
                            else if(tempColor == 148 || tempColor == 255){      //Change allowed in field number
                                temp.setBackground(new Color(255,0,1));
                            }
                            redColor.add(temp);
                        }
                    }
                }
            });
        }

        /*Add actionlisteners to other buttons*/
        buttons[9].addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e){
                if(activeLabel == null){
                    return;
                }
                int activeColor = activeLabel.getBackground().getBlue();
                if(activeColor == 255 || activeColor == 148 || activeColor == 0 || activeColor == 1){
                    activeLabel.setText(" ");
                    for(JLabel red : redColor){
                        if(red.getBackground().getBlue() == 0){
                            red.setBackground(new Color(230,230,230));
                        }
                        else if(red.getBackground().getBlue() == 1){
                            red.setBackground(new Color(255,255,255));
                        }
                    }
                    redColor.clear();
                }
            }

        });

        /*Adjust window size*/
        sudoku.pack();

        sudoku.setVisible(true);
    }

    /*class sameNumber extends AbstractAction{
        public void sameNumber(JLabel label){

            label.setBackground(Color.yellow);
            label.setOpaque(true);
        }*/

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
            }

            if(row == 3){                      //grid is completed go to next grid
                adjRow += 27;                       //adjust starting position
                row = 0;                            //row in  big grid
                position = adjRow;
            }
            //TODO:check if text can be centered with a better method than adding spaces*/

            for(int i = 0; i < line.length(); i++){
                if( line.charAt(i) != '.'){
                    gameGrid[position].setText("" + line.charAt(i));  //6 spaces
                    gameGrid[position].setBackground(new Color(230,230,230));
                }
                else{
                    gameGrid[position].setText(" ");
                }
                column ++ ;
                if(column == 3){                        //we will get to next big grid on the right side
                    position += 7;                      //jump to next Big grid (right side)
                    column = 0;
                }
                else{                                   //else if in same grid, just increment position
                    position++;
                }
            }
            row++;
        }
        /*Close stream*/
        try{
            in.close();
        }
        catch(IOException ex){
            System.out.println("IOException when closing stream");
            System.exit(0);
        }

        isMoveCorrect = Utilities.createCheckArrays(gameGrid);
    }

}
