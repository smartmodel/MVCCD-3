package utilities.window;

import javax.swing.*;

public class STextField extends JTextField {

    private String oldText ;

    public STextField() {
    }

    // Surcherge de la méthode JTextField
    public void setText(String text) {
        super.setText(text);
        oldText = text;
    }
    public String getOldText() {
        return oldText;
    }

    public boolean isUpdated(){
        if (getText() != null){
            return ! getText().equals(oldText);
        } else {
            return oldText == null;
        }

    }
}
