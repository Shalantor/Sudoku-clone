public class UndoEntry{

    private int position;
    private String text;

    public UndoEntry(int position, String text){
        this.position = position;
        this.text = text;
    }

    public int getInt(){
        return position;
    }

    public String getText(){
        return text;
    }

}
