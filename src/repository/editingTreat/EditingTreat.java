package repository.editingTreat;

import console.ViewLogsManager;
import m.MElement;
import m.services.MElementService;
import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDElement;
import mcd.services.MCDElementService;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.Trace;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Fournit les méthodes génériques de déclenchement de traitement de données telles que treatNew(), treatUpdate(), treatRead(), etc.
 */
public abstract class EditingTreat {

    /**
     * Déclenchement de traitement de données pour la création d'un élément (par exemple pour la création d'une entité).
     * Notamment, ouvre l'éditeur attendu en faisant appel à getDialogEditor().
     */
    public MVCCDElement treatNew(Window owner, MVCCDElement parent) {

        DialogEditor fen = getDialogEditor(owner, parent, null, DialogEditor.NEW); //Ouvre l'éditeur attendu
        fen.setVisible(true);
        return fen.getMvccdElementNew();
    }

    /**
     * Déclenchement de traitement de données pour la modification d'un élément.
     */
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

    /**
     * Déclenchement de traitement de données pour la visualisation d'un élément.
     */
    public DialogEditor treatRead(Window owner, MVCCDElement element) {
        DialogEditor fen = getDialogEditor(owner, element.getParent(), element, DialogEditor.READ);
        fen.setVisible(true);
        return fen;
    }


    /**
     * Déclenchement de traitement de données pour la suppression d'un élément.
     */
    public boolean treatDelete (Window owner, MVCCDElement element) {
        String messageTheElement = StringUtils.lowerCase(MessagesBuilder.getMessagesProperty (getPropertyTheElement()));
        String message = MessagesBuilder.getMessagesProperty ("editor.delete.confirm",
                new String[] { messageTheElement, element.getName()});
        boolean confirmDelete = DialogMessage.showConfirmYesNo_No(owner, message) == JOptionPane.YES_OPTION;
        if (confirmDelete){
            removeMVCCDElementInRepository(element);
            element.removeInParent();
            //TODO-0 Il faut supprimer aussi tous les descendants dans la structure arborescente du projet
            element = null;
        }
        return element == null;
    }


    /**
     * Déclenchement de traitement de données pour la suppression des éléments enfants d'un élément.
     */
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

    public Resultat treatCompletness(Window owner, MVCCDElement mvccdElement, boolean showDialog) {
        Resultat resultat = new Resultat();
        PanelInputContent panelInputContent = loadPanelInput(mvccdElement);

        String messageElement = MessagesBuilder.getMessagesProperty(getPropertyTheElement());

        if (datasAdjusted( panelInputContent)) {
            String messageMode  = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change.completness");
            String message = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change",
                    new String[] {messageMode});
            if (showDialog) {
                if (DialogMessage.showConfirmYesNo_Yes(owner, message) == JOptionPane.YES_OPTION) {
                    DialogEditor fen = getDialogEditor(owner, (MElement) mvccdElement.getParent(), mvccdElement, DialogEditor.UPDATE);
                    fen.setVisible(true);
                }
            } else {
                resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
            }
        } else {
            if (!checkInput(panelInputContent)) {
                String elementNameInContext ;
                if (showDialog) {
                    elementNameInContext = mvccdElement.getNameTree();
                } else {
                    elementNameInContext = ((MCDElement) mvccdElement).getNamePath(MElementService.PATHSHORTNAME);
                }
               String message = MessagesBuilder.getMessagesProperty("dialog.completness.error",
                        new String[]{messageElement, elementNameInContext});
                if (showDialog) {
                    message = message + System.lineSeparator() + MessagesBuilder.getMessagesProperty("dialog.question.input");
                    if (DialogMessage.showConfirmYesNo_Yes(owner, message) == JOptionPane.YES_OPTION) {
                        DialogEditor fen = getDialogEditor(owner, (MElement) mvccdElement.getParent(), mvccdElement, DialogEditor.UPDATE);
                        fen.setVisible(true);
                    }
                } else {
                    resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
                }
            } else {
                if (showDialog) {
                    String message = MessagesBuilder.getMessagesProperty("dialog.completness.ok",
                            new String[]{messageElement, mvccdElement.getNameTree()});
                    resultat.add(new ResultatElement(message, ResultatLevel.FATAL));
                    ViewLogsManager.printResultat(resultat);
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

    /**
     * Chaque descendant de EditingTreat doit définir l'éditeur graphique à utiliser.
     */
    protected abstract DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) ;

    protected abstract String getPropertyTheElement();

    protected  void removeMVCCDElementInRepository(MVCCDElement element){
        MVCCDManager.instance().removeMVCCDElementInRepository(element, element.getParent());
    }

    protected  void removeMVVCCDChildInRepository(MVCCDElement child){
        MVCCDManager.instance().removeMVCCDElementInRepository(child, child.getParent());
    }


}
