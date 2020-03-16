package utilities.window.scomponents;

import main.MVCCDManager;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComponent;

import javax.swing.*;

public class STextField extends JTextField implements SComponent {

    private String oldText ;
    //private boolean firstAffectation = true;
    private boolean checkPreSave = false;
    private boolean readOnly = false;
    private PanelInputContent panel;

    public STextField(PanelInputContent panel) {
        this.panel = panel;
        setBorder(BorderFactory.createLineBorder(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_NORMAL));
        setBackground(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_NORMAL);
    }

    // Surcharge de la méthode JTextField
    public void setText(String text) {
        super.setText(text);
        if (! panel.isDataInitialized()) {
            oldText = text;
        }
        //firstAffectation = false;
    }


    public void setText(Integer integer) {
        if (integer != null){
            setText(String.valueOf(integer));
        } else {
            setText("");
        }

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

    public void setIndirectInput(boolean indirect){
        if (indirect) {
            setEnabled(false);
            setForeground(Preferences.SCOMPONENT_INDIRECT_INPUT_FOREGROUND);
        }
    }

}