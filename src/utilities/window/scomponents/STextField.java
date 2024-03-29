package utilities.window.scomponents;

import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;
import utilities.window.scomponents.services.SComponentService;

import javax.swing.*;

/**
 * Est une extension de JTextField.
 */
public class STextField extends JTextField implements SComponent {

    // Valeur d'initialisation
    private String oldText ;
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

    public STextField(IPanelInputContent panel) {
        super();
        this.panel = panel;
        this.setColor(SComponent.COLORNORMAL);
    }

    /**
     * Constructeur d'un champ texte (TextField).
     * <img src="doc-files/UI_NameMissingWhenCreationOfNewEntity.jpg" alt="Contenu manquant dans le champ de saisie de nom lors de la création d'une nouvelle entité">
     * @param panel Le panneau qui contient le composant textField. Le panneau doit réaliser l'interface IPanelInputContent.
     * @param label L'étiquette associtée au champ de saisie.
     */
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

        // Si ce n'est pas un appel directement pour le contrôle de complétude
        if (panel.getEditor() != null) {
            if (panel.getEditor().getMode().equals(DialogEditor.NEW)) {
                updated = true;
            }
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
