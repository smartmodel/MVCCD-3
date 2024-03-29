package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.*;
import project.ProjectElement;
import project.ProjectService;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.services.SComboBoxService;
import window.editor.mcd.relation.genspec.GenSpecEditor;
import window.editor.mcd.relation.genspec.GenSpecInputContent;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

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
            SComboBoxService.selectByText(content.getFieldGenEntity(), entityGen.getNamePath());
            if (initFrozen)  {
                content.getFieldGenEntity().setReadOnly(true);
            }
        }
        if (entitySpec  != null) {
            SComboBoxService.selectByText(content.getFieldSpecEntity(), entitySpec.getNamePath());
            if (initFrozen)  {
                content.getFieldSpecEntity().setReadOnly(true);
            }}


        fen.setVisible(true);
        MCDGeneralization mcdGeneralizationNew = (MCDGeneralization)  fen.getMvccdElementNew();

        if (mcdGeneralizationNew != null) {
            addRelEndsInRepository(mcdGeneralizationNew);
            //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
            //addGSEndInRepository(mcdGeneralizationNew.getGen());
            //addGSEndInRepository(mcdGeneralizationNew.getSpec());
        }

        //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
        if (fen.getMvccdElementNew() != null) {
            if (fen.getMvccdElementNew() instanceof ProjectElement) {
                MVCCDManager.instance().setDatasProjectChanged(true);
            }
        }
        return mcdGeneralizationNew;
    }

    //#MAJ 2021-06-30 Affinement de la trace de modification pour déclencher Save
    /*
    private void addGSEndInRepository(MCDGSEnd mcdGSEnd) {
        MCDContRelEnds parent = (MCDContRelEnds) mcdGSEnd.getParent();
        DefaultMutableTreeNode nodeParent = ProjectService.getNodeById((int) parent.getIdProjectElement());
        MVCCDManager.instance().addNewMVCCDElementInRepository(mcdGSEnd, nodeParent);
    }

     */

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
