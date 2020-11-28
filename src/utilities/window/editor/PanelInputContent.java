package utilities.window.editor;

import main.MVCCDElement;
import preferences.PreferencesManager;
import utilities.Debug;
import utilities.window.PanelContent;
import utilities.window.scomponents.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public abstract class PanelInputContent
        extends PanelContent
        implements IAccessDialogEditor, IPanelInputContent,
                    FocusListener, DocumentListener,  ItemListener {

    protected PanelInput panelInput;
    protected MVCCDElement elementForCheckInput;

    //TODO-1 A voir si panel ne peut pas être la classe elle-même
    protected JPanel panelInputContentCustom = new JPanel();

    protected boolean alreadyFocusGained = false;
    private ArrayList<SComponent> sComponents = new ArrayList<SComponent>();
    //private boolean readOnly = false;
    private boolean dataInitialized = false;

    protected boolean preSaveOk = false;

    public PanelInputContent(PanelInput panelInput) {
        super(panelInput);
        this.panelInput = panelInput;

        // Pour utilisation  uniquement checkDatas
        if (panelInput != null) {
            panelInput.setPanelContent(this);
        }
    }

    protected void start() {
        createContentCustom();
        addContent(panelInputContentCustom);

        // Pour utilisation  uniquement checkDatas
        if (panelInput != null) {
            initOrLoadDatas();
        }
        enabledContent();
    }

    protected abstract void enabledContent();


    public abstract void createContentCustom();

    public boolean checkDatas(SComponent sComponent) {
        resetColorSComponents();
        boolean ok = checkDatasPreSave(sComponent);
        return ok;
    }

    ;

    public boolean checkDatasPreSave(SComponent sComponent) {
        boolean ok = true;
        setPreSaveOk(ok);
        return ok;
    }

    ;

    protected abstract boolean changeField(DocumentEvent e);
    //protected abstract SComponent changeFieldId(DocumentEvent e);


    protected abstract void changeFieldSelected(ItemEvent e);
    //protected abstract void changeFieldIdSelected(ItemEvent e);

    protected abstract void changeFieldDeSelected(ItemEvent e);

    public abstract void loadDatas(MVCCDElement mvccdElementCrt);

    //protected abstract void initDatas(MVCCDElement mvccdElementParent);
    protected abstract void initDatas();

    protected abstract void saveDatas(MVCCDElement mvccdElement);


    @Override
    public void insertUpdate(DocumentEvent e) {
        //SComponent sComponent = changeFieldId(e);
        //sComponent = changeField(e);
        //SComponent sComponent = changeField(e);
        if (alreadyFocusGained) {
            changeField(e);
            enabledButtons();
            enabledContent();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        /*
        if (e.getDocument() instanceof  STextField){
            STextField sTextField = (STextField) e.getDocument();
            sTextField.setColorNormal();
        } */
        //SComponent sComponent = changeFieldId(e);
        //sComponent = changeField(e);
        //SComponent sComponent = changeField(e);
        if (alreadyFocusGained) {
            changeField(e);
            enabledButtons();
            enabledContent();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        //SComponent sComponent = changeFieldId(e);
        //sComponent = changeField(e);
        //SComponent sComponent = changeField(e);
        if (alreadyFocusGained) {
            changeField(e);
            enabledButtons();
            enabledContent();
        }
    }


    @Override
    public void itemStateChanged(ItemEvent e) {


        if (alreadyFocusGained) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                //changeFieldIdSelected(e);
                changeFieldSelected(e);
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                changeFieldDeSelected(e);
            }
            if (e.getSource() instanceof SCheckBox) {
                SCheckBox checkBox = (SCheckBox) e.getSource();
                enableSubPanels(checkBox);
            }
            if (e.getSource() instanceof SComboBox) {
            }
            enabledButtons();
            enabledContent();
        }

    }


    protected void enabledButtons() {

        // Le check doit être fait au début pour remettre l'affichage normal
        // de champs testés ensemble
        if (datasChangedNow()) {
            if (preSaveOk) {
                getButtonsContent().getBtnOk().setEnabled(true);
                getButtonsContent().getBtnApply().setEnabled(true);
            } else {
                getButtonsContent().getBtnOk().setEnabled(false);
                getButtonsContent().getBtnApply().setEnabled(false);
            }
            getButtonsContent().getBtnUndo().setEnabled(true);
        } else {
            getButtonsContent().getBtnUndo().setEnabled(false);
            getButtonsContent().getBtnOk().setEnabled(false);
            getButtonsContent().getBtnApply().setEnabled(false);
        }

        if (getEditor() instanceof DialogEditorNavBtn) {
            DialogEditorNavBtn editor = (DialogEditorNavBtn) getEditor();
            if (!editor.getMode().equals(DialogEditor.NEW)) {
                editor.getNav().getContent().enabledButtons(!datasChangedNow());
            }
        }
    }

    public boolean checkInput(SComponent field, boolean unitaire, ArrayList<String> messagesErrors) {
        if (unitaire) {
            showCheckResultat(messagesErrors);
        }
        field.setErrorInput(messagesErrors.size() > 0);
        if (messagesErrors.size() != 0) {
            if (field.isCheckPreSave()) {
                field.setColor(SComponent.COLORERROR);
            } else {
                if (field.getColor() != SComponent.COLORERROR) {
                    field.setColor(SComponent.COLORWARNING);
                }
            }
        }
        return messagesErrors.size() == 0;
    }

    public void reInitField(STextField field) {
        field.setColor(SComponent.COLORNORMAL);
    }


    public void showCheckResultat(ArrayList<String> messagesErrors) {
        if (getEditor().getButtons() != null) {
            PanelButtonsContent buttonsContent = (PanelButtonsContent) getEditor().getButtons().getPanelContent();
            buttonsContent.clearMessages();
            for (String message : messagesErrors) {
                buttonsContent.addIfNotExistMessage(message);
            }
        }
    }


    public DialogEditor getEditor() {
        // Pour utilisation  uniquement checkDatas
        if (panelInput != null) {
            return panelInput.getEditor();
        }
        return null;
    }

    public PanelButtons getButtons() {
        return getEditor().getButtons();
    }

    public PanelButtonsContent getButtonsContent() {
        return getButtons().getButtonsContent();
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        // pour remettre la fenêtre au premier plan si l'aide est affichée
        getEditor().focusGained(focusEvent);
        getButtonsContent().clearMessages();
        if (!alreadyFocusGained) {
            checkDatas(null);
            enabledButtons();
            alreadyFocusGained = true;
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
        alreadyFocusGained = false;
        getButtonsContent().clearMessages();
    }


    public boolean datasChangedNow() {
        boolean resultat = false;
        for (SComponent sComponent : sComponents) {
            if (PreferencesManager.instance().getApplicationPref().isDEBUG()) {
                if (PreferencesManager.instance().getApplicationPref().getDEBUG_EDITOR_DATAS_CHANGED()) {
                    if (sComponent.checkIfUpdated()) {
                        Debug.println(sComponent.getName() + " - " +sComponent);
                    }
                }
            }
            resultat = resultat || sComponent.checkIfUpdated();
        }
        return resultat;
    }

    public void restartChange() {
        for (SComponent sComponent : sComponents) {
            sComponent.restartChange();
        }
    }

    public void resetDatas() {
        for (SComponent sComponent : sComponents) {
            sComponent.reset();
        }
    }

    public ArrayList<SComponent> getSComponents() {
        return sComponents;
    }

    public void initOrLoadDatas() {
        if (getEditor().getMode().equals(DialogEditor.NEW)) {
            initDatas();
        } else {
            loadDatas(getEditor().getMvccdElementCrt());
        }

        dataInitialized = true;

        // Les données peuvent être ajustées par les méthodes changeXXX de l'éditeur
        if (! getEditor().getMode().equals(DialogEditor.NEW)) {
            loadSimulationChange(getEditor().getMvccdElementCrt());
        }


        checkDatas(null);
        preSaveOk = checkDatasPreSave(null);
    }

    public abstract void loadSimulationChange(MVCCDElement mvccdElementCrt);


    public boolean isDataInitialized() {
        return dataInitialized;
    }

    public void setDataInitialized(boolean dataInitialized) {
        this.dataInitialized = dataInitialized;
    }

    protected void enableSubPanels(SCheckBox sCheckBox) {
        if (sCheckBox.getSubPanel() != null) {
            Component[] components = sCheckBox.getSubPanel().getComponents();
            if (components.length > 0) {
                for (int i = 0; i < components.length; i++) {
                    components[i].setEnabled(sCheckBox.isSelected());
                    if (components[i] instanceof SCheckBox) {
                        SCheckBox checkBoxChild = (SCheckBox) components[i];
                        if (checkBoxChild.getSubPanel() != null) {
                            enableSubPanels(checkBoxChild);
                        }
                    }
                }
            }
        }
    }

    protected void enableSubPanels() {
        for (SCheckBox sCheckBox : getSCheckBoxs()){
            if (sCheckBox.isRootSubPanel()){
                enableSubPanels(sCheckBox);
            }
        }
    }


    protected ArrayList<SCheckBox> getSCheckBoxs() {
        ArrayList<SCheckBox> resultat = new ArrayList<SCheckBox>();
        for (SComponent sComponent : sComponents) {
            if (sComponent instanceof SCheckBox) {
                resultat.add((SCheckBox) sComponent);
            }
        }
        return resultat;
    }

    public void setComponentsReadOnly(boolean readOnly) {
            for (SComponent sComponent : sComponents) {
                sComponent.setReadOnly(readOnly);
            }
    }

    public boolean isPreSaveOk() {
        return preSaveOk;
    }

    public void setPreSaveOk(boolean preSaveOk) {
        this.preSaveOk = preSaveOk;
    }

    protected void resetColorSComponents(){
        for (SComponent sComponent : sComponents){
            if (! (sComponent instanceof SButton)) {
                sComponent.setColor(SComponent.COLORNORMAL);
            }
        }
    }

    public MVCCDElement getElementForCheckInput() {
        return elementForCheckInput;
    }

    protected void reInitDatas(MVCCDElement mvccdElement){

    }
}