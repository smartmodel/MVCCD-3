package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDAssociation;
import mcd.MCDContRelations;
import mcd.MCDEntity;
import mcd.MCDLink;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.services.SComboBoxService;
import window.editor.mcd.relation.link.LinkEditor;
import window.editor.mcd.relation.link.LinkInputContent;

import java.awt.*;

public class MCDLinkEditingTreat extends MCDRelationEditingTreat {


    public  MCDLink treatNew(Window owner,
                             MVCCDElement parent) {

        MCDLink mcdLinkNew = (MCDLink) super.treatNew( owner, parent);

        if (mcdLinkNew != null) {
            addRelEndsInRepository(mcdLinkNew);
        }
        return mcdLinkNew;
    }

    public  MCDLink treatNew(Window owner,
                             MCDContRelations parent,
                             MCDEntity mcdEntity,
                             MCDAssociation mcdAssociation,
                             boolean initFrozen) {

        DialogEditor fen = getDialogEditor(owner, parent, null, DialogEditor.NEW);
        LinkInputContent content = (LinkInputContent) fen.getInput().getInputContent();

        if (mcdEntity  != null){
            SComboBoxService.selectByText(content.getFieldEntity(), mcdEntity.getNamePath(content.getModePathName()));
            if (initFrozen)  {
                content.getFieldEntity().setReadOnly(true);
            }
        }
        if (mcdAssociation  != null) {
            SComboBoxService.selectByText(content.getFieldAssociation(), mcdAssociation.getNameTree());
            if (initFrozen)  {
                content.getFieldAssociation().setReadOnly(true);
            }
        }


        fen.setVisible(true);
        MCDLink mcdLinkNew = (MCDLink)  fen.getMvccdElementNew();

        if (mcdLinkNew != null) {
            addRelEndsInRepository(mcdLinkNew);
        }
        return mcdLinkNew;
    }

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new LinkInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner,
                                           MVCCDElement parent,
                                           MVCCDElement element,
                                           String mode) {
        return new LinkEditor(owner, (MCDContRelations) parent, (MCDLink) element,
                mode, new MCDLinkEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.generalization";
    }



}
