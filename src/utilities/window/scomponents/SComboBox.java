package utilities.window.scomponents;

import main.MVCCDManager;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;

public class SComboBox<S> extends JComboBox<S> implements SComponent {

    public static final String LIGNETIRET = "---";
    public static final String LIGNEVIDE = "";

    //private S oldSelected = null;
    private int oldIndex = -1 ;
    private boolean checkPreSave = false;

    private boolean readOnly = false;
    private PanelInputContent panel;


    public SComboBox(PanelInputContent panel) {
        this.panel = panel ;
        setBorder(BorderFactory.createLineBorder(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_NORMAL));
        setBackground(
                PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_NORMAL);
    }

    public void setSelectedItem(Object item) {
        for (int i = 0; i < getItemCount(); i++) {
            if (item.equals(getItemAt(i))){
                super.setSelectedItem(item);
                setSelectedBase(i);
            }
        }
    }

    public void setSelectedIndex(int index) {
        for (int i = 0; i < getItemCount(); i++) {
            if (index == i ){
                super.setSelectedIndex(index);
                setSelectedBase(i);
            }
        }
    };

    private void setSelectedBase(int i) {
        if (! panel.isDataInitialized()) {
            //oldSelected = getItemAt(i);
            oldIndex = i ;
        }
    }

    public Integer getOldIndex() {
        return oldIndex;
    }

    @Override
    public boolean checkIfUpdated() {
        boolean updated;
        updated = (getSelectedIndex() != oldIndex);

       if (updated) {
            MVCCDManager.instance().datasProjectChangedFromEditor();
        }
        return updated;
    }

    @Override
    public void restartChange() {
        oldIndex = getSelectedIndex();
    }

    @Override
    public void reset() {
        setSelectedIndex(oldIndex);
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