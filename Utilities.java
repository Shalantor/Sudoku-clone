import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.image.*;

public class Utilities{

    static private final int GRID = 9;

    /*Creates the main 3x3 grid on a label,and puts labels in an Array*/
    public static JLabel[] createBigGrid(JLabel label){

        JLabel[] labelArray = new JLabel[GRID];

        for(int i=0 ; i < GRID ; i++){
            labelArray[i] = new JLabel();
            labelArray[i].setBorder(new BevelBorder(BevelBorder.RAISED));
            label.add(labelArray[i]);
        }

        /*Update screen*/
        label.validate();

        return labelArray;
    }

    /*Creates the smaller grid for the actual game field*/
    public static JLabel[] createSmallGrid(JLabel label,JLabel[] labelArray,int start){

        label.setLayout(new GridLayout(3,3,2,2));

        for(int i=0; i < GRID ; i++){
            labelArray[start + i] = new JLabel();
            labelArray[start + i].setBorder(BorderFactory.createLineBorder(Color.cyan));
            labelArray[start + i].setBackground(new Color(255,255,255));
            labelArray[start + i].setOpaque(true);
            label.add(labelArray[start + i]);
        }

        /*Update screen*/
        label.validate();

        return labelArray;
    }

    /*Creates the buttons available for playing for the user*/
    public static void createPlayButtons(JLabel label){

        JButton tempButton;
        ImageIcon tempImage;
        JCheckBox tempCheckBox;

        /*First add the buttons with the numbers*/
        for(int i=0; i < GRID; i++){
            tempButton = new JButton("" + (i+1));
            tempButton.setPreferredSize(new Dimension(70,40));
            label.add(tempButton);
        }

        /*Then add rubber button*/
        tempImage = new ImageIcon("images/rubber.png");
        tempImage = getScaledImage(tempImage.getImage(),20,20);
        tempButton = new JButton(tempImage);
        tempButton.setPreferredSize(new Dimension(70,40));
        label.add(tempButton);

        /*Then add undo button*/
        tempImage = new ImageIcon("images/undo.png");
        tempImage = getScaledImage(tempImage.getImage(),20,20);
        tempButton = new JButton(tempImage);
        tempButton.setPreferredSize(new Dimension(70,40));
        label.add(tempButton);

        /*Add checkbox for solution verification*/
        tempCheckBox = new JCheckBox("    Verify against Solution");
        tempCheckBox.setPreferredSize(new Dimension(295,40));
        label.add(tempCheckBox);

        /*Add button to show solution*/
        tempImage = new ImageIcon("images/solve.png");
        tempImage = getScaledImage(tempImage.getImage(),20,20);
        tempButton = new JButton(tempImage);
        tempButton.setPreferredSize(new Dimension(70,40));
        label.add(tempButton);

        /*Update scren*/
        label.validate();


    }

    /*Scale image for buttons*/
    private static ImageIcon getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        ImageIcon returnImage;

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        returnImage = new ImageIcon(resizedImg);

        return returnImage;
    }

    /*Instantiate board before filling it*/
    public static void emptyBoard(JLabel[] board){
        /*Empty board*/
        for(JLabel label : board){
            label.setText("");
            label.setBackground(new Color(255,255,255));
        }
    }

    /*Create arrays for position checks on boards, like same row and column*/
    public static JLabel[][][] createCheckArrays(JLabel[] board){

        int row = 0,column = 0,iterator = 0;
        JLabel[][] checkSameBox = new JLabel[9][9];
        JLabel[][] checkSameRowOrColumn = new JLabel[9][9];

        /*First create 2darray based on squares*/
        for(; iterator < board.length; iterator++){
            checkSameBox[row][column] = board[iterator];
            int newRow = iterator % 9;
            column++;
            if( newRow != row){
                row = newRow;
                column = 0;
            }
        }

        /*Then create 2dArray based on columns and rows*/
        row = 0;
        column = 0;
        int box = 0,boxRow = 0;
        int rowUpperBound = 0;
        int boxUpperBound = 0;
        int boxStart = 0;
        int times = 3;

        while(true){
            //TODO: Remove when finishedSystem.out.println("BOX: " + box + " BOXROW: " + boxRow);
            checkSameRowOrColumn[row][column] = checkSameBox[box][boxRow];
            /*Update positions of first array*/
            column++;
            if(column == 9){
                column = 0;
                row ++;
                if(row == 9){
                    break;
                }
            }
            /*Update positions of second array*/
            /*Next row*/
            boxRow = (boxRow + 1) % 3;
            if(boxRow == 0){
                box = (box + 1) % 3;
                box += boxUpperBound;
            }
            boxRow += rowUpperBound;
            if(column == 0){
                //System.out.println("Changing boxrow");
                rowUpperBound += 3;
                boxRow = rowUpperBound;
            }
            /*Case where we have processed 3 times consecutively the rows*/
            if(row == times){
                //System.out.println("Changing row");
                times += 3;
                boxUpperBound += 3;
                rowUpperBound = 0;
                boxRow = 0;
                box = row;
            }
        }
        JLabel[][][] returnArray = {checkSameBox, checkSameRowOrColumn};
        return returnArray;
    }
}
