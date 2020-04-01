package repository.editingTreat;

import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.MCDEntities;
import mcd.MCDEntity;
import mcd.MCDModel;
import mcd.MCDModels;
import mcd.services.MCDEntityService;
import newEditor.DialogEditor;
import project.Project;
import window.editor.entity.EntityEditor;
import window.editor.model.ModelEditor;
import window.editor.project.ProjectEditor;

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
