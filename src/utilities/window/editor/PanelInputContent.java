package utilities.window.editor;

import main.MVCCDElement;
import preferences.PreferencesManager;
import utilities.window.PanelContent;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.SComboBox;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public abstract class PanelInputContent
        extends PanelContent
        implements IAccessDialogEditor, FocusListener, DocumentListener,  ItemListener {

    private PanelInput panelInput;
    private boolean alreadyFocusGained = false;
    private ArrayList<SComponent> sComponents = new ArrayList<SComponent>();
    private boolean readOnly = false;

    public PanelInputContent(PanelInput panelInput) {
        super(panelInput);
        this.panelInput = panelInput;
    }

    protected abstract boolean checkDatas();

    public abstract boolean checkDatasPreSave();

    protected abstract void changeField(DocumentEvent e);


    protected abstract void changeField(ItemEvent e);

    public abstract void loadDatas(MVCCDElement mvccdElement);

    protected abstract void initDatas(MVCCDElement mvccdElement);

    public abstract void saveDatas(MVCCDElement mvccdElement);


    @Override
    public void insertUpdate(DocumentEvent e) {
        changeField(e);
        if (alreadyFocusGained) {
            enabledButtons();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changeField(e);
        if (alreadyFocusGained) {
            enabledButtons();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        changeField(e);
        if (alreadyFocusGained) {
            enabledButtons();
        }
    }



    @Override
    public void itemStateChanged(ItemEvent e) {
        changeField(e);
        if (e.getSource() instanceof SCheckBox) {
            SCheckBox checkBox = (SCheckBox) e.getSource();
            enableSubPanels(checkBox);
        }
        if (e.getSource() instanceof SComboBox) {
        }

        if (alreadyFocusGained) {
            enabledButtons();
        }
    }
    protected void enabledButtons() {
        if (datasChangedNow()) {
            if (checkDatasPreSave()) {
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

    }

    public boolean checkInput(STextField field, boolean unitaire, ArrayList<String> messagesErrors) {
        if (unitaire) {
            showCheckResultat(field, messagesErrors);
        }
        if (messagesErrors.size() == 0) {
            field.setBorder(BorderFactory.createLineBorder(
                    PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_NORMAL));
            if (field.isCheckPreSave()) {
                field.setBackground(
                        PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_NORMAL);
            }
        } else {
            field.setBorder(BorderFactory.createLineBorder(
                    PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_LINEBORDER_ERROR));
            if (field.isCheckPreSave()) {
                field.setBackground(
                        PreferencesManager.instance().preferences().EDITOR_SCOMPONENT_BACKGROUND_ERROR);
            }

        }
        return messagesErrors.size() == 0;
    }


    protected void showCheckResultat(STextField field, ArrayList<String> messagesErrors) {
        // Si le panneau des boutons est chargé
        if (getEditor().getButtons() != null) {
            PanelButtonsContent buttonsContent = (PanelButtonsContent) getEditor().getButtons().getPanelContent();
            if (messagesErrors.size() > 0) {
                for (String message : messagesErrors) {
                    buttonsContent.addIfNotExistMessage(message);
                }
            } else {
                buttonsContent.clearMessages();
            }
        }
    }


    public DialogEditor getEditor() {
        return panelInput.getEditor();
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
            checkDatas();
            checkDatasPreSave();
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

    public ArrayList<SComponent> getsComponents() {
        return sComponents;
    }

    protected void initOrLoadDatas() {
        if (getEditor().getMode().equals(DialogEditor.UPDATE)) {
            loadDatas(getEditor().getMvccdElement());
        }
        if (getEditor().getMode().equals(DialogEditor.NEW)) {
            initDatas(getEditor().getMvccdElement());
        }
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

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        if (readOnly){
            for (SComponent sComponent : sComponents) {
                sComponent.setReadOnly(readOnly);
            }
        }
    }
}