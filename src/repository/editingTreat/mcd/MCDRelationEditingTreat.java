package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.*;
import project.ProjectService;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;

public class MCDRelationEditingTreat extends EditingTreat {


    protected void addRelEndsInRepository(MCDRelation mcdRelation) {
        addRelEndInRepository(mcdRelation.getA());
        addRelEndInRepository(mcdRelation.getB());
    }

    private void addRelEndInRepository(MCDRelEnd mcdRelEnd) {
        MCDContRelEnds parent = (MCDContRelEnds) mcdRelEnd.getParent();
        DefaultMutableTreeNode nodeParent = ProjectService.getNodeById((int) parent.getId());
        MVCCDManager.instance().addNewMVCCDElementInRepository(mcdRelEnd, nodeParent);
    }


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return null;
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.relation";
    }

    @Override
    protected void removeMVCCDElementInRepository(MVCCDElement element) {
        MCDRelation mcdRelation = (MCDRelation) element;
        MVCCDManager.instance().removeMCDRelationAndDependantsInRepository(mcdRelation);
    }

    @Override
    public ArrayList<String> treatCompliant(Window owner, MVCCDElement mvccdElement) {
        return null;
    }

    @Override
    public ArrayList<String> treatTransform(Window owner, MVCCDElement mvccdElement) {
        return null;
    }

}
