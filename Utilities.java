import javax.swing.*;
import java.awt.*;

public class Utilities{

    static private final int GRID = 9;

    /*Creates a 3x3 grid on a label,and puts labels in an Array*/
    public static JLabel[] createGrid(JLabel label){

        JLabel[] labelArray = new JLabel[GRID];

        for(int i=0 ; i < GRID ; i++){
            labelArray[i] = new JLabel("hello");
            label.add(labelArray[i]);
        }

        label.validate();

        return labelArray;
    }

}
