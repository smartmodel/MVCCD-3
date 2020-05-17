package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDAssEnd;
import mcd.MCDAssociation;
import mcd.MCDContEndRels;
import mcd.MCDContRelations;
import mcd.services.MCDAssociationService;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import project.ProjectService;
import utilities.window.DialogMessage;
import utilities.window.editor.PanelInputContent;
import window.editor.entity.EntityInputContent;
import window.editor.relation.association.AssociationEditor;
import window.editor.relation.association.AssociationInputContent;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;

public class MCDAssociationEditingTreat extends EditingTreat {


    public  MCDAssociation treatNew(Window owner,
                                          MCDContRelations parent) {

        MCDAssociation mcdAssociationNew = (MCDAssociation) super.treatNew( owner, parent);

        if (mcdAssociationNew != null) {
            addAssEndInRepository(mcdAssociationNew.getFrom());
            addAssEndInRepository(mcdAssociationNew.getTo());
        }
        return mcdAssociationNew;
    }

    private void addAssEndInRepository(MCDAssEnd mcdAssEnd) {
        MCDContEndRels parent = (MCDContEndRels) mcdAssEnd.getParent();
        DefaultMutableTreeNode nodeParent = ProjectService.getNodeById((int) parent.getId());
        MVCCDManager.instance().addNewMVCCDElementInRepository(mcdAssEnd, nodeParent);
    }

/*
    public static void treatCompliant(Window window,
                                      MCDAssociation element) {

        if (MCDAssociationService.check(element).size() == 0){
            String message = MessagesBuilder.getMessagesProperty ("dialog.check.association.ok", new String[] {element.getName()});
            DialogMessage.showOk(window, message);
        } else {
            String message = MessagesBuilder.getMessagesProperty ("dialog.check.association.error", new String[] {element.getName()});
            if (DialogMessage.showConfirmYesNo_Yes(window, message) == JOptionPane.YES_OPTION){
                AssociationEditor fen = new AssociationEditor(window, (MCDContRelations) element.getParent(), element, DialogEditor.UPDATE);
                fen.setVisible(true);
            }
        }
    }
*/

    @Override
    protected ArrayList<String> checkCompliant(MVCCDElement mvccdElement) {
        ArrayList<String> resultat = new ArrayList<String>();
        return resultat;
    }

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new AssociationInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner,
                                           MVCCDElement parent,
                                           MVCCDElement element,
                                           String mode) {
        return new AssociationEditor(owner, (MCDContRelations) parent, (MCDAssociation) element,
                mode, new MCDAssociationEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.association";
    }

}
