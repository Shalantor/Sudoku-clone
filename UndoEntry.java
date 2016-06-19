import javax.swing.*;

public class UndoEntry{

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
