package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDContRelEnds;
import mcd.MCDRelEnd;
import mcd.MCDRelation;
import project.ProjectService;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MCDRelationEditingTreat extends EditingTreat {


    protected void addRelEndsInRepository(MCDRelation mcdRelation) {
        addRelEndInRepository((MCDRelEnd) mcdRelation.getA());
        addRelEndInRepository((MCDRelEnd) mcdRelation.getB());
    }

    private void addRelEndInRepository(MCDRelEnd mcdRelEnd) {
        MCDContRelEnds parent = (MCDContRelEnds) mcdRelEnd.getParent();
        DefaultMutableTreeNode nodeParent = ProjectService.getNodeById((int) parent.getIdProjectElement());
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

}
