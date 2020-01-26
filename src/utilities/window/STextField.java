package utilities.window;

import main.MVCCDManager;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;

import javax.swing.*;

public class STextField extends JTextField implements SComponent{

    private String oldText ;
    private boolean firstAffectation = true;
    private boolean checkPreSave = false;

    public STextField() {
        setBorder(BorderFactory.createLineBorder(Preferences.EDITOR_SCOMPONENT_LINEBORDER_NORMAL));
        setBackground(Preferences.EDITOR_SCOMPONENT_BACKGROUND_NORMAL);
    }

    // Surcharge de la méthode JTextField
    public void setText(String text) {
        super.setText(text);
        if (firstAffectation) {
            oldText = text;
        }
        firstAffectation = false;
    }
    public String getOldText() {
        return oldText;
    }



    public boolean isCheckPreSave() {
        return checkPreSave;
    }


    public void setCheckPreSave(boolean checkPreSave) {
        this.checkPreSave = checkPreSave;
    }


    @Override
    public boolean checkIfUpdated(){
        boolean updated;
        if (StringUtils.isNotEmpty(getText())){
            updated = ! getText().equals(oldText);
        } else {
            updated =  StringUtils.isNotEmpty(oldText);
        }
        if (updated) {
            MVCCDManager.instance().setDatasChanged(true);
        }
        return updated;
    }

    @Override
    public void restartChange(){
        oldText = getText();
    }

    @Override
    public void reset() {
        setText(oldText);
    }


}
