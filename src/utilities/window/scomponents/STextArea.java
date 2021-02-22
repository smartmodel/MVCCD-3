package utilities.window.scomponents;

import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;
import utilities.window.scomponents.services.SComponentService;

import javax.swing.*;

public class STextArea extends JTextArea implements SComponent {


    //TODO-1 - Pas testé en édition (Simple recopie de STextField)

    // Valeur d'initialisation
    private String oldText = null;
    // Erreur empêchant la sauvegarde du formulaire
    private boolean checkPreSave = false;
    // Lecture seule
    private boolean readOnly = false;
    // Panneau contenant le composant
    private IPanelInputContent panel;
    // Mise en évidence d'erreur ou incohérence
    private int color;
    // Erreur
    private boolean errorInput = false;
    // Etiquette attachée au formulaire
    private  JLabel label;

    public STextArea(IPanelInputContent panel) {
        super();
        this.panel = panel;
        this.setColor(SComponent.COLORNORMAL);
    }

    public STextArea(IPanelInputContent panel, JLabel label) {
        super();
        this.panel = panel;
        this.setColor(SComponent.COLORNORMAL);
        this.label = label;
    }

    // Surcharge de la méthode JTextArea
    public void append(String text) {
        if (text == null){
            text = "";
        }
        super.append(text);
        if (! panel.isDataInitialized()) {
            if (oldText == null) {
                oldText = text;
            } else {
                oldText = oldText + text;
            }
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

        // Si ce n'est pas un appel directement pour le contrôle de complétude
        if (panel.getEditor() != null) {
            if (panel.getEditor().getMode().equals(DialogEditor.NEW)) {
                updated = true;
            }
        }
        if (updated) {
            //MVCCDManager.instance().datasProjectChangedFromEditor();
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
