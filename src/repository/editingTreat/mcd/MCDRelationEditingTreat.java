package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import project.ProjectService;
import repository.editingTreat.EditingTreat;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public abstract class MCDRelationEditingTreat extends EditingTreat {


    protected void addRelEndsInRepository(MCDRelation mcdRelation) {
        addRelEndInRepository(mcdRelation.getA());
        addRelEndInRepository(mcdRelation.getB());
    }

    private void addRelEndInRepository(MCDRelEnd mcdRelEnd) {
        MCDContEndRels parent = (MCDContEndRels) mcdRelEnd.getParent();
        DefaultMutableTreeNode nodeParent = ProjectService.getNodeById((int) parent.getId());
        MVCCDManager.instance().addNewMVCCDElementInRepository(mcdRelEnd, nodeParent);
    }


    @Override
    protected void removeMVCCDElementInRepository(MVCCDElement element) {
        MCDRelation mcdRelation = (MCDRelation) element;
        MVCCDManager.instance().removeMCDRelationAndDependantsInRepository(mcdRelation);
    }
}
