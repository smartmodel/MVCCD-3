package utilities.window.scomponents;

import utilities.window.scomponents.services.SComponentService;

import javax.swing.*;

public class SButton extends JButton implements SComponent {

    private boolean checkPreSave = false;
    private int color;
    private boolean errorInput = false;


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
    public void setColor(int color) {
        this.color = color;
        if (color == SComponent.COLORNORMAL){
            SComponentService.colorNormal(this);
        }
        if (color == SComponent.COLORWARNING){
            SComponentService.colorWarning(this);
        }
        if (color == SComponent.COLORERROR){
            SComponentService.colorError(this);
        }
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setErrorInput(boolean errorInput) {
        this.errorInput = errorInput;
    }

    @Override
    public boolean isErrorInput() {
        return errorInput;
    }


    @Override
    public void setCheckPreSave(boolean checkPreSave) {

    }

    @Override
    public boolean isCheckPreSave() {
        return checkPreSave;
    }
}
