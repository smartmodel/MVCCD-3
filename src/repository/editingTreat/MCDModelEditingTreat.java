package repository.editingTreat;

import main.MVCCDManager;
import mcd.MCDModel;
import mcd.MCDContModels;
import utilities.window.editor.DialogEditor;
import window.editor.model.ModelEditor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MCDModelEditingTreat {

    public static void treatNew(Window owner, MCDContModels mcdContModels, DefaultMutableTreeNode node) {

        ModelEditor fen = new ModelEditor(owner, mcdContModels, null,
                DialogEditor.NEW, DialogEditor.MODEL);
        fen.setVisible(true);

        MCDModel mcdModelNew = (MCDModel) fen.getMvccdElementNew();
        if (mcdModelNew != null) {
           MVCCDManager.instance().addNewMVCCDElementInRepository(mcdModelNew, node);
        }
    }


    public static void treatUpdate(Window owner,
                                   MCDModel mcdModel) {
        ModelEditor fen = new ModelEditor(owner , (MCDContModels) mcdModel.getParent(), mcdModel,
                DialogEditor.UPDATE, DialogEditor.MODEL);
        fen.setVisible(true);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
    }

}
