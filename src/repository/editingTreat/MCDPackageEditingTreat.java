package repository.editingTreat;

import main.MVCCDManager;
import mcd.MCDElement;
import mcd.MCDModel;
import mcd.MCDModels;
import mcd.MCDPackage;
import mcd.interfaces.IMCDModel;
import newEditor.DialogEditor;
import window.editor.model.ModelEditor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MCDPackageEditingTreat {

    public static void treatNew(Window owner, MCDElement parent, DefaultMutableTreeNode node) {

        ModelEditor fen = new ModelEditor(owner, parent, null,
                DialogEditor.NEW, ModelEditor.PACKAGE);
        fen.setVisible(true);

        MCDPackage mcdPackageNew = (MCDPackage) fen.getMvccdElementNew();
        if (mcdPackageNew != null) {
           MVCCDManager.instance().addNewMVCCDElementInRepository(mcdPackageNew, node);
        }
    }


    public static void treatUpdate(Window owner,
                                   MCDPackage mcdPackage) {
        ModelEditor fen = new ModelEditor(owner , (MCDElement) mcdPackage.getParent(), mcdPackage,
                DialogEditor.UPDATE, ModelEditor.PACKAGE);
        fen.setVisible(true);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
    }

}
