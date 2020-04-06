package utilities.window.scomponents;

import main.MVCCDManager;
import utilities.window.scomponents.services.SComponentService;

import javax.swing.*;

public class SCheckBox extends JCheckBox implements SComponent {

    private Boolean oldSelected = null;
    private boolean checkPreSave = false;
    private JPanel subPanel;  // Si la case à cocher doit valider/invalider un panneau dépendant
    private boolean rootSubPanel = false ; //Si la case à cocher est la racine d'un arbre de panneaux dépendants

    private boolean readOnly = false;
    private IPanelInputContent panel;

    public SCheckBox(IPanelInputContent panel){
        super();
        init(panel);
    }
    public SCheckBox(IPanelInputContent panel, String label) {
        super(label);
        init(panel);
    }

    private void init(IPanelInputContent panel){
        this.panel = panel;
        this.setColorNormal();
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

}
