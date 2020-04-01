package repository.editingTreat;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import mcd.MCDEntities;
import mcd.MCDEntity;
import mcd.services.MCDAttributeService;
import mcd.services.MCDEntityService;
import messages.MessagesBuilder;
import newEditor.DialogEditor;
import project.ProjectService;
import utilities.Debug;
import utilities.window.DialogMessage;
import window.editor.attribute.AttributeEditor;
import window.editor.entity.EntityEditor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MCDAttributeEditingTreat  {


    //TODO-0 Supprimer le paramètre node dans toutes les méthode treatNew du package
    public static MCDAttribute treatNew(Window owner,
                                        MCDContAttributes parent) {

        System.out.println("MCDContAttributes :  " + parent.getName()  +    "  " + parent.getId() );
        DefaultMutableTreeNode nodeParent = ProjectService.getNodeById((int) parent.getId());
        System.out.println("MCDContAttributes :  " + nodeParent.getUserObject().toString());
                AttributeEditor fen = MCDAttributeEditingTreat.showEditorAttribute(owner, parent, null, DialogEditor.NEW);
        MCDAttribute mcdAttributeNew = (MCDAttribute) fen.getMvccdElementNew();
        if (mcdAttributeNew != null) {
            MVCCDManager.instance().addNewMVCCDElementInRepository(mcdAttributeNew, nodeParent);
        }
        return mcdAttributeNew;
    }



    public static void treatUpdate(Window owner,
                                   MCDAttribute element) {

        AttributeEditor fen = showEditorAttribute(owner , (MCDContAttributes) element.getParent(), element, EntityEditor.UPDATE);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
    }




    public static void treatCompliant(Window window,
                                      MCDAttribute element) {

        if (MCDAttributeService.check(element).size() == 0){
            String message = MessagesBuilder.getMessagesProperty ("dialog.check.entity.ok", new String[] {element.getName()});
            DialogMessage.showOk(window, message);
        } else {
            String message = MessagesBuilder.getMessagesProperty ("dialog.check.entity.error", new String[] {element.getName()});
            if (DialogMessage.showConfirmYesNo_Yes(window, message) == JOptionPane.YES_OPTION){
                Debug.println("Correction de l'entité");
                AttributeEditor fen = showEditorAttribute(window, (MCDContAttributes) element.getParent(), element, DialogEditor.UPDATE);
            }
        }
    }



    public static AttributeEditor showEditorAttribute(Window owner,
                                                      MCDContAttributes parent,
                                                      MCDAttribute mcdAttribute,
                                                      String mode) {
        AttributeEditor fen = new AttributeEditor(owner , parent, mcdAttribute, mode);
        fen.setVisible(true);
        return fen;
    }


}
