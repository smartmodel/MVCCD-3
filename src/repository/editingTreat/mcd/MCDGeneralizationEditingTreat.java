package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import project.ProjectService;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.services.SComboBoxService;
import window.editor.relation.association.AssociationInputContent;
import window.editor.relation.genspec.GenSpecEditor;
import window.editor.relation.genspec.GenSpecInputContent;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;

public class MCDGeneralizationEditingTreat extends MCDRelationEditingTreat {


    public  MCDGeneralization treatNew(Window owner,
                                       MVCCDElement parent) {

        MCDGeneralization mcdGeneralizationNew = (MCDGeneralization) super.treatNew( owner, parent);

        if (mcdGeneralizationNew != null) {
            addRelEndsInRepository(mcdGeneralizationNew);
        }
        return mcdGeneralizationNew;
    }

    public  MCDGeneralization treatNew(Window owner,
                                       MCDContRelations parent,
                                       MCDEntity entityGen,
                                       MCDEntity entitySpec,
                                       boolean initFrozen) {

        DialogEditor fen = getDialogEditor(owner, parent, null, DialogEditor.NEW);
        GenSpecInputContent content = (GenSpecInputContent) fen.getInput().getInputContent();

        if (entityGen  != null){
            SComboBoxService.selectByText(content.getFieldGenEntity(), entityGen.getNamePath(content.getModePathName()));
            if (initFrozen)  {
                content.getFieldGenEntity().setReadOnly(true);
            }
        }
        if (entitySpec  != null) {
            SComboBoxService.selectByText(content.getFieldSpecEntity(), entitySpec.getNamePath(content.getModePathName()));
            if (initFrozen)  {
                content.getFieldSpecEntity().setReadOnly(true);
            }}


        fen.setVisible(true);
        MCDGeneralization mcdGeneralizationNew = (MCDGeneralization)  fen.getMvccdElementNew();

        if (mcdGeneralizationNew != null) {
            addGSEndInRepository(mcdGeneralizationNew.getGen());
            addGSEndInRepository(mcdGeneralizationNew.getSpec());
        }
        return mcdGeneralizationNew;
    }

    private void addGSEndInRepository(MCDGSEnd mcdGSEnd) {
        MCDContRelEnds parent = (MCDContRelEnds) mcdGSEnd.getParent();
        DefaultMutableTreeNode nodeParent = ProjectService.getNodeById((int) parent.getId());
        MVCCDManager.instance().addNewMVCCDElementInRepository(mcdGSEnd, nodeParent);
    }

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new GenSpecInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner,
                                           MVCCDElement parent,
                                           MVCCDElement element,
                                           String mode) {
        return new GenSpecEditor(owner, (MCDContRelations) parent, (MCDGeneralization) element,
                mode, new MCDGeneralizationEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.generalization";
    }


}
