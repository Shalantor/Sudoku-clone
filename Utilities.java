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
        tempImage = new ImageIcon("rubber.png");
        tempImage = getScaledImage(tempImage.getImage(),20,20);
        tempButton = new JButton(tempImage);
        tempButton.setPreferredSize(new Dimension(70,40));
        label.add(tempButton);

        /*Then add undo button*/
        tempImage = new ImageIcon("undo.png");
        tempImage = getScaledImage(tempImage.getImage(),20,20);
        tempButton = new JButton(tempImage);
        tempButton.setPreferredSize(new Dimension(70,40));
        label.add(tempButton);

        /*Add checkbox for solution verification*/
        tempCheckBox = new JCheckBox("    Verify against Solution");
        tempCheckBox.setPreferredSize(new Dimension(295,40));
        label.add(tempCheckBox);

        /*Add button to show solution*/
        tempImage = new ImageIcon("solve.png");
        tempImage = getScaledImage(tempImage.getImage(),20,20);
        tempButton = new JButton(tempImage);
        tempButton.setPreferredSize(new Dimension(70,40));
        label.add(tempButton);

        /*Update scren*/
        label.validate();


    }

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


}
