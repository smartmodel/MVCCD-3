package utilities.window;

import javax.swing.*;

public class SButton extends JButton implements SComponent{

    public SButton(String text){
        super(text);
    }

    private boolean readOnly = false;
    @Override
    public boolean checkIfUpdated() {
        return false;
    }

    @Override
    public void restartChange() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        if (readOnly) {
            super.setEnabled(false);
        }
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    public void setEnabled(boolean enabled){
        if (! isReadOnly()){
            super.setEnabled(enabled);
        } else{
            super.setEnabled(false);
        }
    }
}
