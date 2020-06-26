package repository.editingTreat;

import m.MElement;
import main.MVCCDElement;
import main.MVCCDManager;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.apache.commons.lang.StringUtils.lowerCase;

public abstract class EditingTreat {

    public MVCCDElement treatNew(Window owner, MVCCDElement parent) {

        DialogEditor fen = getDialogEditor(owner, parent, null, DialogEditor.NEW);
        fen.setVisible(true);
        MVCCDElement newElement = fen.getMvccdElementNew();
        return newElement;
    }





    public boolean treatUpdate(Window owner, MVCCDElement element) {
        MVCCDElement parentBefore = element.getParent();
        DialogEditor fen = getDialogEditor(owner, element.getParent(), element, DialogEditor.UPDATE);
        fen.setVisible(true);


        MVCCDElement parentAfter = element.getParent();
        if (parentBefore != parentAfter) {
            MVCCDManager.instance().changeParentMVCCDElementInRepository(element, parentBefore);
        }
        return fen.isDatasChanged();
    }

    public DialogEditor treatRead(Window owner, MVCCDElement element) {
        DialogEditor fen = getDialogEditor(owner, element.getParent(), element, DialogEditor.READ);
        fen.setVisible(true);
        return fen;
    }


    public boolean treatDelete (Window owner, MVCCDElement element) {
        String messageTheElement = StringUtils.lowerCase(MessagesBuilder.getMessagesProperty (getPropertyTheElement()));
        String message = MessagesBuilder.getMessagesProperty ("editor.delete.confirm",
                new String[] { messageTheElement, element.getName()});
        boolean confirmDelete = DialogMessage.showConfirmYesNo_No(owner, message) == JOptionPane.YES_OPTION;
        if (confirmDelete){
            removeMVCCDElementInRepository(element);
            element.removeInParent();
            element = null;
        }
        return element == null;
    }



    public void treatDeleteChilds (Window owner, MVCCDElement element) {
        String messageTheElement = StringUtils.lowerCase(MessagesBuilder.getMessagesProperty (getPropertyTheElement()));
        String message = MessagesBuilder.getMessagesProperty ("editor.delete.childs.confirm",
                new String[] {element.getName()});
        boolean confirmDelete = DialogMessage.showConfirmYesNo_No(owner, message) == JOptionPane.YES_OPTION;
        if (confirmDelete){
            System.out.println("Avant delete  " + element.getChilds().size());
            for (int i = element.getChilds().size() - 1  ; i >= 0 ;  i--) {
                System.out.println("Delete  " + i);
                MVCCDElement child = element.getChilds().get(i);
                removeMVVCCDChildInRepository(child);
                //MVCCDManager.instance().removeMVCCDElementInRepository(child, element);

                child.removeInParent();
                child = null;
            }
        }
    }


    public void treatCompliant(Window owner, MVCCDElement mvccdElement) {
        PanelInputContent panelInputContent = loadPanelInput(mvccdElement);

        String messageElement = MessagesBuilder.getMessagesProperty(getPropertyTheElement());

        if (datasAdjusted( panelInputContent)) {
            String messageMode  = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change.compliant");
            String message = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change",
                    new String[] {messageMode});
            if (DialogMessage.showConfirmYesNo_Yes(owner, message) == JOptionPane.YES_OPTION) {
                DialogEditor fen = getDialogEditor(owner, (MElement) mvccdElement.getParent(), mvccdElement, DialogEditor.UPDATE);
                fen.setVisible(true);
            }
        } else {
            if (!checkInput( panelInputContent)) {
                String message = MessagesBuilder.getMessagesProperty("dialog.input.error",
                        new String[]{messageElement, mvccdElement.getNameTree()});
                if (DialogMessage.showConfirmYesNo_Yes(owner, message) == JOptionPane.YES_OPTION) {
                    DialogEditor fen = getDialogEditor(owner, (MElement) mvccdElement.getParent(), mvccdElement, DialogEditor.UPDATE);
                    fen.setVisible(true);
                }
            } else {
                ArrayList<String> messagesCompliant = checkCompliant(mvccdElement);
                if (messagesCompliant.size() == 0) {
                    String message = MessagesBuilder.getMessagesProperty("dialog.compliant.ok",
                            new String[]{messageElement, mvccdElement.getNameTree()});
                    DialogMessage.showOk(owner, message);
                } else {
                    String message = MessagesBuilder.getMessagesProperty("dialog.compliant.error",
                            new String[]{messageElement, mvccdElement.getNameTree()});
                    DialogMessage.showError(owner, message);
                }
            }
        }
    }

    protected abstract ArrayList<String> checkCompliant(MVCCDElement mvccdElement);



    public PanelInputContent loadPanelInput(MVCCDElement element){
        PanelInputContent panelInputContent = getPanelInputContent((MVCCDElement) element);
        panelInputContent.createContentCustom();
        panelInputContent.loadDatas((MVCCDElement) element);
        panelInputContent.setDataInitialized(true);
        panelInputContent.loadSimulationChange((MVCCDElement) element);

        return panelInputContent;
    }


    public boolean checkInput(PanelInputContent panelInputContent){
         return panelInputContent.checkDatas(null);
    }

    public boolean datasAdjusted(PanelInputContent panelInputContent){
        return panelInputContent.datasChangedNow();
    }

    protected abstract PanelInputContent getPanelInputContent(MVCCDElement element);

    protected abstract DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) ;

    protected abstract String getPropertyTheElement();


    protected  void removeMVCCDElementInRepository(MVCCDElement element){
        MVCCDManager.instance().removeMVCCDElementInRepository(element, element.getParent());
    }

    protected  void removeMVVCCDChildInRepository(MVCCDElement child){
        MVCCDManager.instance().removeMVCCDElementInRepository(child, child.getParent());
    }


}
