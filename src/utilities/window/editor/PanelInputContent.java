package utilities.window.editor;

import main.MVCCDElement;
import mcd.MCDEntity;
import preferences.Preferences;
import utilities.window.PanelContent;
import utilities.window.SComponent;
import utilities.window.STextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public abstract class PanelInputContent
        extends PanelContent
        implements IAccessDialogEditor, FocusListener, DocumentListener {

    private PanelInput panelInput;
    private boolean alreadyFocusGained = false;
    private ArrayList<SComponent> sComponents = new ArrayList<SComponent>();

    public PanelInputContent(PanelInput panelInput) {
        super(panelInput);
        this.panelInput = panelInput;
    }

    protected abstract boolean checkDatas();

    public abstract boolean checkDatasPreSave();

    protected abstract void changeField(DocumentEvent e);

    public abstract void saveDatas(MVCCDElement mmvccdElement) ;


        @Override
    public void insertUpdate(DocumentEvent e) {
        System.out.println("insertUpdate");
        changeField(e);
        if (alreadyFocusGained) {enabledButtons();}
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changeField(e);
        if (alreadyFocusGained) {enabledButtons();}
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        changeField(e);
        if (alreadyFocusGained) {enabledButtons();}
    }

    protected  void enabledButtons(){
            System.out.println("Changed:  " + datasChangedNow());
            if (datasChangedNow() ) {
                if (checkDatasPreSave()) {
                     getButtonsContent().getBtnOk().setEnabled(true);
                    getButtonsContent().getBtnApply().setEnabled(true);
                } else {
                    getButtonsContent().getBtnOk().setEnabled(false);
                    getButtonsContent().getBtnApply().setEnabled(false);
                }
                getButtonsContent().getBtnReset().setEnabled(true);
            } else {
                getButtonsContent().getBtnReset().setEnabled(false);
                getButtonsContent().getBtnOk().setEnabled(false);
                getButtonsContent().getBtnApply().setEnabled(false);
            }

    }
    public boolean checkInput(STextField field, boolean unitaire, ArrayList<String> messagesErrors) {
      System.out.println("Field: " + field.getText() + "  "  + field.toString());
        if (unitaire) {
            showCheckResultat(field, messagesErrors);
        }
        if (messagesErrors.size() == 0){
            field.setBorder(BorderFactory.createLineBorder(Preferences.EDITOR_SCOMPONENT_LINEBORDER_NORMAL));
            if (field.isCheckPreSave()){
                field.setBackground(Preferences.EDITOR_SCOMPONENT_BACKGROUND_NORMAL);
            }
        } else {
            field.setBorder(BorderFactory.createLineBorder(Preferences.EDITOR_SCOMPONENT_LINEBORDER_ERROR));
            if (field.isCheckPreSave()){
                field.setBackground(Preferences.EDITOR_SCOMPONENT_BACKGROUND_ERROR);
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


    public DialogEditor getEditor(){
        return panelInput.getEditor();
    }

    public PanelButtons getButtons(){
        return getEditor().getButtons();
    }

    public PanelButtonsContent getButtonsContent(){
        return getButtons().getButtonsContent();
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        // pour remettre la fenêtre au premier plan si l'aide est affichée
        getEditor().focusGained(focusEvent);
        getButtonsContent().clearMessages();
        if (! alreadyFocusGained){
            System.out.println("! already focus...");
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
        for (SComponent sComponent : sComponents){
            resultat = resultat  || sComponent.checkIfUpdated();
        }
        return resultat;
     }

    public void restartChange() {
        for (SComponent sComponent : sComponents){
            sComponent.restartChange();
        }
    }

    public void resetDatas() {
        for (SComponent sComponent : sComponents){
            sComponent.reset();
        }
    }

    public ArrayList<SComponent> getsComponents() {
        return sComponents;
    }
}
