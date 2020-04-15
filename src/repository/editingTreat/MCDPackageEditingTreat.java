package repository.editingTreat;

import main.MVCCDManager;
import mcd.MCDElement;
import mcd.MCDPackage;
import utilities.window.editor.DialogEditor;
import window.editor.model.ModelEditor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MCDPackageEditingTreat extends EditingTreat{

    /*
    public static void treatNew(Window owner, MCDElement parent, DefaultMutableTreeNode node) {

        ModelEditor fen = new ModelEditor(owner, parent, null,
                DialogEditor.NEW, ModelEditor.PACKAGE);
        fen.setVisible(true);

        MCDPackage mcdPackageNew = (MCDPackage) fen.getMvccdElementNew();
        if (mcdPackageNew != null) {
           MVCCDManager.instance().addNewMVCCDElementInRepository(mcdPackageNew, node);
        }
    }

     */

/*
    public static void treatUpdate(Window owner,
                                   MCDPackage mcdPackage) {
        MCDElement parentBefore = (MCDElement) mcdPackage.getParent();
        ModelEditor fen = new ModelEditor(owner , (MCDElement) mcdPackage.getParent(), mcdPackage,
                DialogEditor.UPDATE, ModelEditor.PACKAGE);
        fen.setVisible(true);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
        MCDElement parentAfter =  (MCDElement) mcdPackage.getParent();
        if (parentBefore != parentAfter){
            parentBefore.getChilds().remove(mcdPackage);
            MVCCDManager.instance().changeParentMVCCDElementInRepository(mcdPackage, parentBefore );
        }
    }
*/
    @Override
    protected DialogEditor getDialogEditor(Window owner, MCDElement parent, MCDElement mcdElement, String mode) {
        return new ModelEditor(owner , parent, mcdElement,
                mode, ModelEditor.PACKAGE);
    }
}
