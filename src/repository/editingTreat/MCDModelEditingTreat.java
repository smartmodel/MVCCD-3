package repository.editingTreat;

import main.MVCCDManager;
import mcd.MCDModel;
import mcd.MCDModels;
import utilities.window.editor.DialogEditor;
import window.editor.model.ModelEditor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MCDModelEditingTreat {

    public static void treatNew(Window owner, MCDModels mcdModels, DefaultMutableTreeNode node) {

        ModelEditor fen = new ModelEditor(owner, mcdModels, null,
                DialogEditor.NEW, ModelEditor.MODEL);
        fen.setVisible(true);

        MCDModel mcdModelNew = (MCDModel) fen.getMvccdElementNew();
        if (mcdModelNew != null) {
           MVCCDManager.instance().addNewMVCCDElementInRepository(mcdModelNew, node);
        }
    }


    public static void treatUpdate(Window owner,
                                   MCDModel mcdModel) {
        ModelEditor fen = new ModelEditor(owner , (MCDModels) mcdModel.getParent(), mcdModel,
                DialogEditor.UPDATE, ModelEditor.MODEL);
        fen.setVisible(true);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
    }

}
