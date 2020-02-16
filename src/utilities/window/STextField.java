package utilities.window;

import main.MVCCDManager;
import org.apache.commons.lang.StringUtils;
import preferences.PreferencesManager;

import javax.swing.*;

public class STextField extends JTextField implements SComponent{

    private String oldText ;
    private boolean firstAffectation = true;
    private boolean checkPreSave = false;
    boolean readOnly = false;

    public STextField() {
        setBorder(BorderFactory.createLineBorder(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_NORMAL));
        setBackground(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_NORMAL);
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
            MVCCDManager.instance().datasProjectChangedFromEditor();
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
