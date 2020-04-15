package repository.editingTreat;

import main.MVCCDManager;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import mcd.services.MCDEntityService;
import messages.MessagesBuilder;
import utilities.Debug;
import utilities.window.DialogMessage;
import window.editor.entity.EntityEditor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MCDEntityEditingTreat {


    public static void treatNew(Window owner,
                                MCDContEntities mcdContEntities,
                                DefaultMutableTreeNode nodeEntities) {

        EntityEditor fen = MCDEntityEditingTreat.showEditorEntity(owner, mcdContEntities, null, EntityEditor.NEW);
        MCDEntity mcdEntityNew = (MCDEntity) fen.getMvccdElementNew();
        if (mcdEntityNew != null) {
            MVCCDManager.instance().addNewMVCCDElementInRepository(mcdEntityNew, nodeEntities);
        }

    }


    public static void treatUpdate(Window owner,
                                   MCDEntity mcdEntity) {

        EntityEditor fen = showEditorEntity(owner , (MCDContEntities) mcdEntity.getParent(), mcdEntity, EntityEditor.UPDATE);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
    }

    public static void treatCompliant(Window window, MCDEntity mcdEntity) {
        if (MCDEntityService.check(mcdEntity).size() == 0){
            String message = MessagesBuilder.getMessagesProperty ("dialog.check.entity.ok", new String[] {mcdEntity.getName()});
            DialogMessage.showOk(window, message);
        } else {
            String message = MessagesBuilder.getMessagesProperty ("dialog.check.entity.error", new String[] {mcdEntity.getName()});
            if (DialogMessage.showConfirmYesNo_Yes(window, message) == JOptionPane.YES_OPTION){
                Debug.println("Correction de l'entité");
                EntityEditor fen = showEditorEntity(window, (MCDContEntities) mcdEntity.getParent(), mcdEntity, EntityEditor.UPDATE);
            }
        }
    }



    public static EntityEditor showEditorEntity(Window owner,
                                          MCDContEntities parent,
                                          MCDEntity mcdEntity,
                                          String mode) {
        EntityEditor fen = new EntityEditor(owner , parent, mcdEntity, mode);
        fen.setVisible(true);
        return fen;
    }

}
