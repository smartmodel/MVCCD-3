package repository.editingTreat.mcd;

import exceptions.CodeApplException;
import main.MVCCDElement;
import mcd.MCDAssEnd;
import mcd.MCDAssociation;
import mcd.MCDContRelations;
import mcd.MCDEntity;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.relation.association.AssociationEditor;

import java.awt.*;

public class MCDAssEndEditingTreat extends MCDRelEndEditingTreat {

    private MCDEntity mcdEntityFrom = null;
    private MCDEntity mcdEntityTo = null;

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {

        MCDAssEnd mcdAssEnd = (MCDAssEnd) element;
        MCDEntity mcdEntityParent = (MCDEntity) parent.getParent() ;
        MCDContRelations mcdContRelations = (MCDContRelations) mcdEntityParent.getParent().getBrotherByClassName(MCDContRelations.class.getName());
        MCDAssociation mcdAssociation = null;
        if (mcdAssEnd != null){
            mcdAssociation = mcdAssEnd.getMcdAssociation();
        }
        return new AssociationEditor(owner , mcdContRelations, mcdAssociation, mode,
                new MCDAssociationEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.association.end";
    }

    public MVCCDElement treatNew(Window owner, MVCCDElement parent) {
        MCDEntity mcdEntityParent = (MCDEntity) parent.getParent() ;
        MCDContRelations mcdContRelations = (MCDContRelations) mcdEntityParent.getParent().getBrotherByClassName(MCDContRelations.class.getName());

        if(mcdEntityFrom == null){
            throw new CodeApplException("Une association ne peut pas être créée sans entité de départ...");
        }
        MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
        MCDAssociation newMCDAssociation = mcdAssociationEditingTreat.treatNew(owner, mcdContRelations,
                mcdEntityFrom, null, null, true);
        if (newMCDAssociation != null){
            return newMCDAssociation.getFrom();  // Extrémité mcdEntityContext
        } else {
            return null;
        }
    }

    public void setMcdEntityFrom(MCDEntity mcdEntityFrom) {
        this.mcdEntityFrom = mcdEntityFrom;
    }

    public void setMcdEntityTo(MCDEntity mcdEntityTo) {
        this.mcdEntityTo = mcdEntityTo;
    }
}
