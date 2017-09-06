import javax.swing.*;

/*Class for the individual fields in the gamegrid*/
public class Field extends JLabel{

    /*Value of field is given by default, player cannot alter it*/
    public boolean isPreset = false;
    /*To Change the color between fields that match the solution and those who do not*/
    public boolean verify = false;
    /*Coordinates*/
    public int xCoordinate;
    public int yCoordinate;

    public Field(String input){
        super(input,SwingConstants.CENTER);
    }

}
