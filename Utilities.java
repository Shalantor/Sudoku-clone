import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;

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
    public static JLabel[] createSmallGrid(JLabel label){

        JLabel[] labelArray = new JLabel[GRID];
        label.setLayout(new GridLayout(3,3,2,2));

        for(int i=0; i < GRID ; i++){
            labelArray[i] = new JLabel();
            labelArray[i].setBorder(BorderFactory.createLineBorder(Color.cyan));
            labelArray[i].setBackground(new Color(255,255,255));
            labelArray[i].setOpaque(true);
            label.add(labelArray[i]);
        }

        /*Update screen*/
        label.validate();

        return labelArray;
    }

}
