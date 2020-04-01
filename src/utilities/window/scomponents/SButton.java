package utilities.window.scomponents;

import utilities.window.scomponents.services.SComponentService;

import javax.swing.*;

public class SButton extends JButton implements SComponent {

    private boolean checkPreSave = false;

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


    @Override
    public void setColorError() {
        SComponentService.colorError(this);
    }

    @Override
    public void setColorWarning() {
        SComponentService.colorWarning(this);
    }

    @Override
    public void setColorNormal() {
        SComponentService.colorNormal(this);
    }

    @Override
    public boolean isCheckPreSave() {
        return checkPreSave;
    }
}
