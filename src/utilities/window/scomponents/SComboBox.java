package utilities.window.scomponents;

import utilities.window.editor.DialogEditor;
import utilities.window.scomponents.services.SComponentService;

import javax.swing.*;

public class SComboBox<S> extends JComboBox<S> implements SComponent {

    public static final String LINEDASH = "---";
    public static final String LINEWHITE = "";

    public static final int INDEXOUTBOUND = -1;

    //private S oldSelected = null;
    private int oldIndex = -1 ;
    private Object oldObject = null;
    private boolean checkPreSave = false;

    private boolean readOnly = false;
    private IPanelInputContent panel;
    private int color;
    private boolean errorInput = false;
    private  JLabel label = null;

    // Contrôle de changement de valeur pour les listes de valeurs utilisées pour aliement un champ
    // Exemple aid avec la liste : num, numDep en mettant à false
    // boolean checkAdjustAutomatically = true;



    public SComboBox(IPanelInputContent panel) {
        this.panel = panel ;
        this.setColor(SComponent.COLORNORMAL);
    }

    public SComboBox(IPanelInputContent panel, JLabel label) {
        this.panel = panel ;
        this.label = label;
        this.setColor(SComponent.COLORNORMAL);
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
                setSelectedBase(index);
            }
        }
    };

    private void setSelectedBase(int i) {
        if (! panel.isDataInitialized()) {
            //oldSelected = getItemAt(i);
            oldIndex = i ;
            oldObject = getItemAt(i);
         }
    }

    public Integer getOldIndex() {
        return oldIndex;
    }

    // Pour pouvoir lors d'un load() remplacé une valeur reçue devenue inexistante par une valeur valeur ou asence de valeur
    // Cas pratque : MPDR Chargement d'une connexion qui a été supprimée entre-temps
    public void forceUpdated() {
        oldIndex = -1;
    }


    @Override
    public boolean checkIfUpdated() {
        boolean updated;
        updated = (getSelectedIndex() != oldIndex) || (getSelectedItem() != oldObject);

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
    public void restartChange() {
        oldIndex = getSelectedIndex();
        oldObject = getSelectedItem();
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


    @Override
    public void setCheckPreSave(boolean checkPreSave) {

    }

    @Override
    public boolean isCheckPreSave() {
        return checkPreSave;
    }

    @Override
    public JLabel getJLabel() {
        return label;
    }

    public boolean isSelectedEmpty(){
        String content = (String) this.getSelectedItem();
        return  (content.equals(LINEDASH) || content.equals(LINEWHITE)) ;
    }

    public boolean isNotSelectedEmpty(){
        return  ! isSelectedEmpty() ;
    }

    public int getItemEmptyIndex(){
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemAt(i).equals(LINEWHITE) || getItemAt(i).equals(LINEDASH)){
                return INDEXOUTBOUND;
            }
        }
        return -1 ;
    }

    public void setSelectedEmpty(){
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemAt(i).equals(LINEWHITE) || getItemAt(i).equals(LINEDASH)){
                super.setSelectedIndex(i);
                setSelectedBase(i);
            }
        }
    }

    public String getFirstItem(){
        return (String) super.getItemAt(0);
    }

    public void setSelectedFirst() {
        super.setSelectedIndex(0);
        setSelectedBase(0);
    }

    /*
    public boolean isCheckAdjustAutomatically() {
        return checkAdjustAutomatically;
    }

    public void setCheckAdjustAutomatically(boolean checkAdjustAutomatically) {
        this.checkAdjustAutomatically = checkAdjustAutomatically;
    }

     */
}
