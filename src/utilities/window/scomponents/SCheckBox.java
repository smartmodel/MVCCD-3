package utilities.window.scomponents;

import utilities.window.editor.DialogEditor;
import utilities.window.scomponents.services.SComponentService;

import javax.swing.*;

public class SCheckBox extends JCheckBox implements SComponent {

    private Boolean oldSelected = null;
    private boolean checkPreSave = false;
    private JPanel subPanel;  // Si la case à cocher doit valider/invalider un panneau dépendant
    private boolean rootSubPanel = false ; //Si la case à cocher est la racine d'un arbre de panneaux dépendants

    private boolean readOnly = false;
    private IPanelInputContent panel;
    private int color;
    private boolean errorInput = false;

    private JLabel label;


    public SCheckBox(IPanelInputContent panel){
        super();
        init(panel);
    }
    public SCheckBox(IPanelInputContent panel, JLabel label) {
        super();
        init(panel, label);
    }

    private void init(IPanelInputContent panel){
        this.panel = panel;
        this.setColor(SComponent.COLORNORMAL);
    }

    private void init(IPanelInputContent panel, JLabel label){
        init(panel);
        this.label = label;
    }


    // Surcharge de la méthode JCheckBox
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (! panel.isDataInitialized()) {
            oldSelected = selected;
        }
    }

    public Boolean getOldSelected() {
        return oldSelected;
    }



    public boolean isCheckPreSave() {

        return checkPreSave;
    }

    @Override
    public JLabel getJLabel() {
        return null;
    }


    public void setCheckPreSave(boolean checkPreSave) {
        this.checkPreSave = checkPreSave;
    }


    @Override
    public boolean checkIfUpdated() {
        boolean updated;
        if (oldSelected != null) {
            updated = (isSelected() != oldSelected);
        } else {
            updated = true;
        }

        // Si ce n'est pas un appel directement pour le contrôle de conformité
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
        oldSelected = isSelected();
    }

    @Override
    public void reset() {
        setSelected(oldSelected);
    }

    public JPanel getSubPanel() {
        return subPanel;
    }

    public void setSubPanel(JPanel subPanel) {
        this.subPanel = subPanel;
    }

    public boolean isRootSubPanel() {
        return rootSubPanel;
    }

    public void setRootSubPanel(boolean rootSubPanel) {
        this.rootSubPanel = rootSubPanel;
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


}
