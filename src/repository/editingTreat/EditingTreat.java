package repository.editingTreat;

import console.Console;
import m.MElement;
import main.MVCCDElement;
import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.MCDElement;
import mcd.services.MCDElementService;
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
            for (int i = element.getChilds().size() - 1  ; i >= 0 ;  i--) {
                MVCCDElement child = element.getChilds().get(i);
                removeMVVCCDChildInRepository(child);

                child.removeInParent();
                child = null;
            }
        }
    }


    public ArrayList<String> treatCompletness(Window owner, MVCCDElement mvccdElement, boolean showDialog) {
        ArrayList <String> resultat = new ArrayList <String>();
        PanelInputContent panelInputContent = loadPanelInput(mvccdElement);

        String messageElement = MessagesBuilder.getMessagesProperty(getPropertyTheElement());

        if (datasAdjusted( panelInputContent)) {
            String messageMode  = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change.completness");
            String message = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change",
                    new String[] {messageMode});
            resultat.add(message);
            if (showDialog) {
                if (DialogMessage.showConfirmYesNo_Yes(owner, message) == JOptionPane.YES_OPTION) {
                    DialogEditor fen = getDialogEditor(owner, (MElement) mvccdElement.getParent(), mvccdElement, DialogEditor.UPDATE);
                    fen.setVisible(true);
                }
            }
        } else {
            if (!checkInput(panelInputContent)) {
                String elementNameInContext ;
                if (showDialog) {
                    elementNameInContext = mvccdElement.getNameTree();
                } else {
                    elementNameInContext = ((MCDElement) mvccdElement).getNamePath(MCDElementService.PATHSHORTNAME);
                }
               String message = MessagesBuilder.getMessagesProperty("dialog.completness.error",
                        new String[]{messageElement, elementNameInContext});
                resultat.add(message);
                if (showDialog) {
                    message = message + System.lineSeparator() + MessagesBuilder.getMessagesProperty("dialog.question.input");
                    if (DialogMessage.showConfirmYesNo_Yes(owner, message) == JOptionPane.YES_OPTION) {
                        DialogEditor fen = getDialogEditor(owner, (MElement) mvccdElement.getParent(), mvccdElement, DialogEditor.UPDATE);
                        fen.setVisible(true);
                    }
                }
            } else {
                if (showDialog) {
                    String message = MessagesBuilder.getMessagesProperty("dialog.completness.ok",
                            new String[]{messageElement, mvccdElement.getNameTree()});
                    DialogMessage.showOk(owner, message);
                }
            }
        }
        return resultat;
    }


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

    public abstract ArrayList<String> treatCompliant(
            Window owner, MVCCDElement mvccdElement);


    protected void treatCompliantFinishMessages(Window owner, MVCCDElement mvccdElement, ArrayList<String> messages) {
        String message = "";
        if (messages.size() == 0 ){
            MCDElement mcdElement = (MCDElement) mvccdElement;
            String messageElement = MessagesBuilder.getMessagesProperty(getPropertyTheElement());

            message = MessagesBuilder.getMessagesProperty("dialog.compliant.ok",
                    new String[]{messageElement, mcdElement.getNamePath(MCDElementService.PATHSHORTNAME)});
        }

        if (messages.size() > 0 ){
            MCDElement mcdElement = (MCDElement) mvccdElement;
            String messageElement = MessagesBuilder.getMessagesProperty(getPropertyTheElement());

            message = MessagesBuilder.getMessagesProperty("dialog.compliant.error",
                    new String[]{messageElement, mcdElement.getNamePath(MCDElementService.PATHSHORTNAME)});
            message = message + System.lineSeparator() + MessagesBuilder.getMessagesProperty("dialog.compliant.error.console");

        }
        //TODO-1 Erreur si < 0

        Console.printMessage(message);
        DialogMessage.showOk(owner, message);
     }
}
