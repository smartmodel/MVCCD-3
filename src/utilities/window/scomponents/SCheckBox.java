package utilities.window.scomponents;

import main.MVCCDManager;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;

public class SCheckBox extends JCheckBox implements SComponent {

    private Boolean oldSelected = null;
    private boolean checkPreSave = false;
    private JPanel subPanel;  // Si la case à cocher doit valider/invalider un panneau dépendant
    private boolean rootSubPanel = false ; //Si la case à cocher est la racine d'un arbre de panneaux dépendants

    private boolean readOnly = false;
    private PanelInputContent panel;

    public SCheckBox(PanelInputContent panel){
        super();
        init(panel);
    }
    public SCheckBox(PanelInputContent panel, String label) {
        super(label);
        init(panel);
    }

    private void init(PanelInputContent panel){
        this.panel = panel;
        setBorder(BorderFactory.createLineBorder(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_NORMAL));
        setBackground(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_NORMAL);
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
        if (updated) {
            MVCCDManager.instance().datasProjectChangedFromEditor();
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
        } else{
            super.setEnabled(false);
        }
    }


}
