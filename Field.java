import javax.swing.*;

/*Class for the individual fields in the gamegrid*/
public class Field extends JLabel{

    public boolean isPreset = false;
    public boolean verify = false;
    public int xCoordinate;
    public int yCoordinate;

    public Field(String input){
        super(input,SwingConstants.CENTER);
    }

}
