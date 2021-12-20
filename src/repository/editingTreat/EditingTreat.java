package repository.editingTreat;

import connections.ConElement;
import console.ViewLogsManager;
import console.WarningLevel;
import m.MElement;
import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDElement;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.ProjectElement;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import java.awt.*;

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
        //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
        if (fen.getMvccdElementNew() != null) {
            if (fen.getMvccdElementNew() instanceof ProjectElement) {
                MVCCDManager.instance().setDatasProjectChanged(true);
            }
        }
        return fen.getMvccdElementNew();
    }

    public MVCCDElement treatNewTransitory(Window owner, MVCCDElement parent) {

        DialogEditor fen = getDialogEditor(owner, parent, null, DialogEditor.NEW); //Ouvre l'éditeur attendu
        //#MAJ 2021-07-31 Spéficité d'un élément transitoire
        fen.setNewElementTransitory(true);
        fen.setVisible(true);
        //#MAJ 2021-07-31 Spéficité d'un élément transitoire
        // Pas de changement pour un élément transitoire
        /*
        if (fen.getMvccdElementNew() != null) {
            if (fen.getMvccdElementNew() instanceof ProjectElement) {
                MVCCDManager.instance().setDatasProjectChanged(true);
            }
        }

         */

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

        //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
        if (fen.isDatasChanged()) {
            //TODO-1 A voir car le changement d'une préférence d'application --> Save Project !
            // Cela pourrait être pertinent de conserver tel quel !
            if ( (element instanceof ProjectElement) || (element instanceof Preferences)){
                MVCCDManager.instance().setDatasProjectChanged(true);
            }
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
        // Pour l'affichage des extrémités de relation avec un nom parlant
        String elementName = element.getNameTree();
        if (element instanceof MElement){
            elementName = ((MElement)element).getNameTreePath();
        }
        String message = MessagesBuilder.getMessagesProperty ("editor.delete.confirm",
                //new String[] { messageTheElement, element.getName()});
                new String[] { messageTheElement, elementName});
        boolean confirmDelete = DialogMessage.showConfirmYesNo_No(owner, message) == JOptionPane.YES_OPTION;
        if (confirmDelete){
            //removeMVCCDElementInRepository(element);
            element.delete();
            if (element instanceof ProjectElement) {
                MVCCDManager.instance().setDatasProjectChanged(true);
            }
            return true;
        }
        return false;
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
            if (element instanceof ProjectElement) {
                MVCCDManager.instance().setDatasProjectChanged(true);
            }
            for (int i = element.getChilds().size() - 1  ; i >= 0 ;  i--) {
                MVCCDElement child = element.getChilds().get(i);
                child.delete();
            }
        }
    }

    public boolean treatCompletness(Window owner, MVCCDElement mvccdElement, boolean showDialog) {

        boolean ok = true;
        PanelInputContent panelInputContent = loadPanelInput(mvccdElement);
        String messageElement = MessagesBuilder.getMessagesProperty(getPropertyTheElement());

        if (datasAdjusted( panelInputContent)) {
           if (showDialog) {
               String messageMode  = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change.completness");
               String message = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change",
                       new String[] {messageMode});
               if (DialogMessage.showConfirmYesNo_Yes(owner, message) == JOptionPane.YES_OPTION) {
                    DialogEditor fen = getDialogEditor(owner, (MElement) mvccdElement.getParent(), mvccdElement, DialogEditor.UPDATE);
                    fen.setVisible(true);
                }
            } else {
                // TODO-0 - Faire de class + nameTree + id une méthode
                String name = mvccdElement.getNameTree();
                if (mvccdElement instanceof MElement){
                    name = ((MElement) mvccdElement).getNameTreePath();
                }
                String id = "-";
                if(mvccdElement instanceof ProjectElement){
                    id = "" + ((ProjectElement) mvccdElement).getIdProjectElement();
                }
                String messageMode  = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change.without.dialog",
                        new String[] {mvccdElement.getClass().getName(), name, id});
               String message = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change",
                       new String[] {messageMode});
               ViewLogsManager.printMessage(message, WarningLevel.INFO);
               ok = false;
            }
        } else {
            if (!checkInput(panelInputContent)) {
                String elementNameInContext = null;
                if (showDialog) {
                    elementNameInContext = mvccdElement.getNameTree();
                } else {
                    if ( mvccdElement instanceof MCDElement) {
                        elementNameInContext = ((MCDElement) mvccdElement).getNameTreePath();
                    }
                    if ( mvccdElement instanceof ConElement) {
                        elementNameInContext = ((ConElement) mvccdElement).getNamePath();
                    }
                }
               String message = MessagesBuilder.getMessagesProperty("dialog.completness.error",
                        new String[]{messageElement, elementNameInContext});
                if (showDialog) {
                    message = message + System.lineSeparator() + MessagesBuilder.getMessagesProperty("dialog.question.input");
                    if (DialogMessage.showConfirmYesNo_Yes(owner, message) == JOptionPane.YES_OPTION) {
                        //DialogEditor fen = getDialogEditor(owner, (MElement) mvccdElement.getParent(), mvccdElement, DialogEditor.UPDATE);
                        // Utilisation pour les connexions qui n'héritent pas de MElement
                        //#MAJ 2021-11-15 Utilisation de complétude pour les connexions
                        DialogEditor fen = getDialogEditor(owner, mvccdElement.getParent(), mvccdElement, DialogEditor.UPDATE);
                        fen.setVisible(true);
                    }
                } else {
                    ViewLogsManager.printMessage(message, WarningLevel.INFO);
                    ok = false;
                }
            } else {
                if (showDialog) {
                    String message = MessagesBuilder.getMessagesProperty("dialog.completness.ok",
                            new String[]{messageElement, mvccdElement.getNameTree()});
                    ViewLogsManager.printMessage(message, WarningLevel.INFO);
                    DialogMessage.showOk(owner, message);
                }
            }
        }
        return ok;
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


}
