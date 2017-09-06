import javax.swing.*;

/*A list of UndoEntries is stored, to be able to Undo moves while playing*/
public class UndoEntry{

    /*Save the label and the respective text in it*/
    private JLabel label;
    private String text;

    public UndoEntry(JLabel label, String text){
        this.label = label;
        this.text = text;
    }

    public JLabel getLabel(){
        return label;
    }

    public String getText(){
        return text;
    }

}
