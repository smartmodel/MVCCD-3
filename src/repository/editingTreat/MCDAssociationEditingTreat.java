package repository.editingTreat;

import main.MVCCDManager;
import mcd.MCDAssociation;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import mcd.MCDRelations;
import mcd.services.MCDAssociationService;
import mcd.services.MCDAttributeService;
import messages.MessagesBuilder;
import newEditor.DialogEditor;
import project.ProjectService;
import utilities.Debug;
import utilities.window.DialogMessage;
import window.editor.attribute.AttributeEditor;
import window.editor.entity.EntityEditor;
import window.editor.relation.association.AssociationEditor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MCDAssociationEditingTreat {


    public static MCDAssociation treatNew(Window owner,
                                          MCDRelations parent) {

        System.out.println("MCDRelations :  " + parent.getName()  +    "  " + parent.getId() );
        DefaultMutableTreeNode nodeParent = ProjectService.getNodeById((int) parent.getId());
        System.out.println("MCDRelations :  " + nodeParent.getUserObject().toString());
                AssociationEditor fen = MCDAssociationEditingTreat.showEditorAssociation(owner, parent, null, DialogEditor.NEW);
        MCDAssociation mcdAssociationNew = (MCDAssociation) fen.getMvccdElementNew();
        if (mcdAssociationNew != null) {
            MVCCDManager.instance().addNewMVCCDElementInRepository(mcdAssociationNew, nodeParent);
        }
        return mcdAssociationNew;
    }



    public static void treatUpdate(Window owner,
                                   MCDAssociation element) {

        AssociationEditor fen = showEditorAssociation(owner , (MCDRelations) element.getParent(), element, EntityEditor.UPDATE);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
    }




    public static void treatCompliant(Window window,
                                      MCDAssociation element) {

        if (MCDAssociationService.check(element).size() == 0){
            String message = MessagesBuilder.getMessagesProperty ("dialog.check.association.ok", new String[] {element.getName()});
            DialogMessage.showOk(window, message);
        } else {
            String message = MessagesBuilder.getMessagesProperty ("dialog.check.association.error", new String[] {element.getName()});
            if (DialogMessage.showConfirmYesNo_Yes(window, message) == JOptionPane.YES_OPTION){
                AssociationEditor fen = showEditorAssociation(window, (MCDRelations) element.getParent(), element, DialogEditor.UPDATE);
            }
        }
    }



    public static AssociationEditor showEditorAssociation(Window owner,
                                                      MCDRelations parent,
                                                      MCDAssociation mcdAssociation,
                                                      String mode) {
        AssociationEditor fen = new AssociationEditor(owner , parent, mcdAssociation, mode);
        fen.setVisible(true);
        return fen;
    }


}
