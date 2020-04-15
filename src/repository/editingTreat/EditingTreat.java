package repository.editingTreat;

import main.MVCCDManager;
import mcd.MCDElement;
import mcd.MCDPackage;
import project.ProjectElement;
import project.ProjectService;
import utilities.window.editor.DialogEditor;
import window.editor.model.ModelEditor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public abstract class EditingTreat {

    public void treatNew(Window owner, MCDElement parent) {

        DialogEditor fen = getDialogEditor(owner, parent, null, DialogEditor.NEW);
        fen.setVisible(true);

        MCDElement newMCDElement = (MCDElement) fen.getMvccdElementNew();
        if (newMCDElement != null) {
            ProjectElement parentChooseByUser= (ProjectElement) newMCDElement.getParent();
            DefaultMutableTreeNode nodeParent = ProjectService.getNodeById(parentChooseByUser.getId());
            MVCCDManager.instance().addNewMVCCDElementInRepository(newMCDElement, nodeParent);
        }
    }

    public void treatUpdate(Window owner, MCDElement mcdElement){
        MCDElement parentBefore = (MCDElement) mcdElement.getParent();
        DialogEditor fen = getDialogEditor(owner, (MCDElement) mcdElement.getParent(), mcdElement, DialogEditor.UPDATE);
        fen.setVisible(true);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
        MCDElement parentAfter =  (MCDElement) mcdElement.getParent();
        if (parentBefore != parentAfter){
            //parentBefore.getChilds().remove(mcdElement);  Réalisé par save de l'éditeur
            MVCCDManager.instance().changeParentMVCCDElementInRepository(mcdElement, parentBefore );
        }
    }

    protected abstract DialogEditor getDialogEditor(Window owner, MCDElement parent, MCDElement mcdElement, String mode) ;
}
