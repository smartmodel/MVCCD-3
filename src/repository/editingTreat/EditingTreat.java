package repository.editingTreat;

import m.MElement;
import main.MVCCDElement;
import main.MVCCDManager;
import messages.MessagesBuilder;
import project.ProjectElement;
import project.ProjectService;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;

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

        /* L'éventuel changement de parent se fait lors de la clotûre définitive
           de la fenêtre d'édition et non à chaque sauvegarde car le code est probablement
           inutilement compliqué pour un cas rare (le changement dans l'éditeur) */
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

    public void treatCompliant(Window owner, MVCCDElement mvccdElement) {
        //MVCCDElement mvccdElement = (MElement) element;

        String messageElement = MessagesBuilder.getMessagesProperty(getPropertyTheElement());
        if (!checkInput(mvccdElement)) {
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

    protected abstract ArrayList<String> checkCompliant(MVCCDElement mvccdElement);





    public boolean checkInput(MVCCDElement element){
        PanelInputContent panelInputContent = getPanelInputContent((MVCCDElement) element);
        panelInputContent.createContentCustom();
        panelInputContent.loadDatas((MVCCDElement) element);

        return panelInputContent.checkDatas(null);
    }

    protected abstract PanelInputContent getPanelInputContent(MVCCDElement element);

    protected abstract DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) ;

    protected abstract String getPropertyTheElement();



}
