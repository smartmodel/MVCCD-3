package utilities.window.scomponents;

import main.MVCCDManager;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;
import utilities.window.scomponents.services.SComponentService;

import javax.swing.*;

public class STextField extends JTextField implements SComponent {

    private String oldText ;
    //private boolean firstAffectation = true;
    private boolean checkPreSave = false;
    private boolean readOnly = false;
    private IPanelInputContent panel;
    private int color;
    private boolean errorInput = false;
    private  JLabel label;

    public STextField(IPanelInputContent panel) {
        super();
        this.panel = panel;
        this.setColor(SComponent.COLORNORMAL);
    }

    public STextField(IPanelInputContent panel, JLabel label) {
        super();
        this.panel = panel;
        this.setColor(SComponent.COLORNORMAL);
        this.label = label;
    }

    // Surcharge de la méthode JTextField
    public void setText(String text) {
        if (text == null){
            text = "";
        }
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

        // Si ce n'est pas un appel directement pour le contrôle de conformité
        if (panel.getEditor() != null) {
            if (panel.getEditor().getMode().equals(DialogEditor.NEW)) {
                updated = true;
            }
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
            if (label != null) label.setEnabled(enabled);
        } else{
            super.setEnabled(false);
            if (label != null) label.setEnabled(false);
        }
    }


    public void setVisible(boolean visible){
        super.setVisible(visible);
        if (label != null) label.setVisible(visible);
    }



    public void setIndirectInput(boolean indirect){
        if (indirect) {
            setEnabled(false);
            setForeground(Preferences.SCOMPONENT_INDIRECT_INPUT_FOREGROUND);
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

    public JLabel getJLabel() {
        return label;
    }
}
