package repository.editingTreat.mcd;

import exceptions.CodeApplException;
import main.MVCCDElement;
import mcd.*;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.relation.link.LinkEditor;

import java.awt.*;

public class MCDLinkEndEditingTreat extends MCDRelEndEditingTreat {

    private MCDEntity mcdEntity = null;
    private MCDAssociation mcdAssociation = null;

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        MCDLink link = ((MCDLinkEnd)element).getMcdLink();
        MCDContRelations contRelations = (MCDContRelations) link.getParent();
        return new LinkEditor(owner , contRelations, link, mode,
                new MCDLinkEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.link.end";
    }

    public MVCCDElement treatNew(Window owner, MVCCDElement parent) {
        MCDEntity mcdEntityParent = (MCDEntity) parent.getParent();
        MCDContRelations mcdContRelations = (MCDContRelations) mcdEntityParent.getParent().getBrotherByClassName(MCDContRelations.class.getName());

        if ((mcdEntity == null) && (mcdAssociation == null)) {
            throw new CodeApplException("Un lien d'entité associative ne peut être créé qui si une entité ou une association n:n est définie...");
        } else if ((mcdEntity != null) && (mcdAssociation != null)) {
            throw new CodeApplException("Un lien d'entité associative ne peut être créé que si seul une entité ou une association est définie...");
        } else {
            MCDLinkEditingTreat mcdLinkEditingTreat = new MCDLinkEditingTreat();
            MCDLink newMCDLink = mcdLinkEditingTreat.treatNew(owner, mcdContRelations,
                    mcdEntity, mcdAssociation, true);

            if (newMCDLink != null) {
                if (mcdEntity != null) {
                    return newMCDLink.getEndEntity();
                } else {
                    return newMCDLink.getEndAssociation();
                }
            } else {
                return null;
            }
        }
    }

    public void setMcdEntity(MCDEntity mcdEntity) {
        this.mcdEntity = mcdEntity;
    }

    public void setMcdAssociation(MCDAssociation mcdAssociation) {
        this.mcdAssociation = mcdAssociation;
    }
}
