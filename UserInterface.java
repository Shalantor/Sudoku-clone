import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.*;
import java.io.*;

public class UserInterface{

    /*TODO:remove tempGameGrid*/
    public JLabel[] tempGameGrid;
    private Field[][][] isMoveCorrect ;        //First 2d array is for same box, second is for row and columns
    public Field[] gameGrid= new Field[81];
    private static final int GRID = 9;
    private Field activeLabel = null;
    private String linkData;
    private AbstractButton[] buttons = new AbstractButton[13];
    public static final int HISTORYSIZE = 20;
    private ArrayList<Field> sameColors = new ArrayList<Field>();//Use for marking labels with same content when selecting
    private ArrayList<Field> redColor = new ArrayList<Field>();//use when input is not valid for that field
    private ArrayList<UndoEntry> history = new ArrayList<UndoEntry>();//use for history when undoing actions
    private SudokuSolver solver;

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
        JLabel topLabel = new JLabel("",SwingConstants.CENTER);
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
        for(Field label : gameGrid){
            label.addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e){
                    if(activeLabel == label){           //check if this is the activeLabel
                        return;
                    }
                    clearRed();
                    label.setBackground(new Color(240,240,148));
                    if(activeLabel != null ){
                        if(activeLabel.isPreset){       //Color of label is gray
                            activeLabel.setBackground(new Color(230,230,230));
                        }
                        else if(!activeLabel.isPreset){
                            activeLabel.setBackground(new Color(255,255,255));  //Color is white
                        }
                    }
                    activeLabel = label;
                    /*Now check for same color labels*/
                    if(!sameColors.isEmpty()){
                        if(activeLabel.getText().charAt(0) != sameColors.get(0).getText().charAt(0)){
                            for(Field temp : sameColors){                      //restore colors
                                if(temp.isPreset){       //Color of label is gray
                                    temp.setBackground(new Color(230,230,230));
                                }
                                else if(!temp.isPreset){
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
                    for(Field temp: gameGrid){              //update colors of other labels with same content
                        if(temp.getText().charAt(0) == activeChar){
                            temp.setBackground(new Color(240,240,148));  //Color is white
                            sameColors.add(temp);
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
                    if(activeLabel == null || activeLabel.isPreset){
                        return;
                    }
                    UndoEntry entry = new UndoEntry(activeLabel,activeLabel.getText());
                    history.add(entry);
                    if( history.size() > HISTORYSIZE){  //if too large remove first
                        history.remove(0);
                    }
                    activeLabel.setText(buttonText);
                    /*Now check labels in same box*/
                    /*First find correct box*/
                    Field[] searchBox = null;
                    loop:
                    for( Field[] box : isMoveCorrect[0] ){
                        for( Field temp : box){
                            if(temp == activeLabel){
                                searchBox = box;
                                break loop;
                            }
                        }
                    }
                    /*Now check for same number in a field*/
                    for(Field temp : searchBox){
                        int tempColor = temp.getBackground().getBlue();
                        if( temp!=activeLabel && temp.getText().equals(buttonText)){
                            temp.setBackground(new Color(255,0,0));
                            redColor.add(temp);
                        }
                    }
                    /*Now check for same row or column*/
                    /*Find row of label*/
                    Field[] search = null;
                    int searchIndex = 0;
                    loop:
                    for( Field[] row : isMoveCorrect[1]){
                        for( Field temp : row){
                            if ( temp == activeLabel){
                                search = row;
                                searchIndex = Arrays.asList(row).indexOf(temp);
                                break loop;
                            }
                        }
                    }
                    /*Now find same in row*/
                    for(Field temp : search){
                        int tempColor = temp.getBackground().getBlue();
                        if( temp!= activeLabel && temp.getText().equals(buttonText)){
                            temp.setBackground(new Color(255,0,0));
                            redColor.add(temp);
                        }
                    }
                    /*Now same column*/
                    for(int i=0; i < 9; i++){
                        Field temp = isMoveCorrect[1][i][searchIndex];
                        int tempColor = temp.getBackground().getBlue();
                        if( temp!= activeLabel && temp.getText().equals(buttonText)){
                            temp.setBackground(new Color(255,0,0));
                            redColor.add(temp);
                        }
                    }
                }
            });
        }

        /*Add actionlisteners to other buttons*/
        /*Erase button*/
        buttons[9].addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e){
                if(activeLabel == null){
                    return;
                }
                if(!activeLabel.isPreset){
                    activeLabel.setText(" ");
                    clearRed();
                }
            }

        });

        /*Undo button*/
        buttons[10].addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e){
                if(history.isEmpty()){
                    return;
                }
                for(Field red : redColor){
                    if(red.getBackground().getBlue() == 0){
                        red.setBackground(new Color(230,230,230));
                    }
                    else if(red.getBackground().getBlue() == 1){
                        red.setBackground(new Color(255,255,255));
                    }
                }
                redColor.clear();
                UndoEntry entry = history.get(history.size() - 1);
                String text = entry.getText();
                JLabel label = entry.getLabel();
                label.setText(text);
                history.remove(entry);
            }
        });

        /*Solve button*/
        buttons[11].addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e){
                if(!solver.finished){
                    topLabel.setText("Solution not ready yet.");
                }
                else{
                    topLabel.setText("");
                    int[][] solution = solver.solution;
                    for(int i =0; i < 9; i++){
                        for( int j =0; j < 9; j++){
                            Integer set = new Integer(solution[i][j]);
                            isMoveCorrect[0][i][j].setText(set.toString());
                        }
                    }
                }
            }
        });

        buttons[12].addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e){
                if(!solver.finished){
                    topLabel.setText("Solution not ready yet.");
                }
                else{
                    topLabel.setText("");
                    int[][] solution = solver.solution;
                    for(int i =0; i < 9; i++){
                        for( int j =0; j < 9; j++){
                            Integer set = new Integer(solution[i][j]);
                            if(isMoveCorrect[0][i][j].getText().equals(set.toString())){
                                isMoveCorrect[0][i][j].setBackground(new Color (0,128,255));
                            }
                        }
                    }
                }
            }
        });

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
        String parseToSolver = "";
        while( (line = in.readLine() ) != null){

            parseToSolver += line;

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
                    gameGrid[position].isPreset = true;
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

        solver = new SudokuSolver(parseToSolver);
        solver.run();

        isMoveCorrect = Utilities.createCheckArrays(gameGrid);
    }

    //clear red fields
    private void clearRed(){
        for(Field red : redColor){
            if(red.isPreset){
                red.setBackground(new Color(230,230,230));
            }
            else{
                red.setBackground(new Color(255,255,255));
            }
        }
        redColor.clear();                   //empty list
    }

}
