package console;

public class ResultatInStartElement {

    String      text ;
    boolean     dialog ;

    public ResultatInStartElement(String text, boolean dialog) {
        this.text = text;
        this.dialog = dialog;
    }

    public String getText() {
        return text;
    }

    public boolean isDialog() {
        return dialog;
    }
}
