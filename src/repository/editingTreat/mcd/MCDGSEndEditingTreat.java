package repository.editingTreat.mcd;

import exceptions.CodeApplException;
import main.MVCCDElement;
import mcd.MCDContRelations;
import mcd.MCDEntity;
import mcd.MCDGSEnd;
import mcd.MCDGeneralization;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.relation.genspec.GenSpecEditor;

import java.awt.*;

public class MCDGSEndEditingTreat extends MCDRelEndEditingTreat {

    private MCDEntity mcdEntityGen = null;
    private MCDEntity mcdEntitySpec = null;

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }


    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        MCDGeneralization generalization = ((MCDGSEnd) element).getMcdGeneralization();
        MCDContRelations contRelations = (MCDContRelations) generalization.getParent();
        return new GenSpecEditor(owner, contRelations, generalization, mode,
                new MCDGeneralizationEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.generalization.end";
    }


    public MVCCDElement treatNew(Window owner, MVCCDElement parent) {
        MCDEntity mcdEntityParent = (MCDEntity) parent.getParent();
        MCDContRelations mcdContRelations = (MCDContRelations) mcdEntityParent.getParent().getBrotherByClassName(MCDContRelations.class.getName());

        if ((mcdEntityGen == null) && (mcdEntitySpec == null)) {
            throw new CodeApplException("Une généralisation/spécialisation ne peut être créée (à partir d'une extrémité d'entité) qui si une entité généralisée ou spécialisée est définie...");
        } else if ((mcdEntityGen != null) && (mcdEntitySpec != null)) {
            throw new CodeApplException("Une généralisation/spécialisation ne peut être créée que si une seule entité généralisée ou spécialisée est définie...");
        } else {
            MCDGeneralizationEditingTreat mcdGeneralizationEditingTreat = new MCDGeneralizationEditingTreat();
            MCDGeneralization newMCDGeneralization = mcdGeneralizationEditingTreat.treatNew(owner, mcdContRelations,
                    mcdEntityGen, mcdEntitySpec, true);

            if (newMCDGeneralization != null) {
                if (mcdEntityGen != null) {
                    return newMCDGeneralization.getGen();
                } else {
                    return newMCDGeneralization.getSpec();
                }
            } else {
                return null;
            }
        }
    }

    public void setMcdEntityGen(MCDEntity mcdEntityGen) {
        this.mcdEntityGen = mcdEntityGen;
    }

    public void setMcdEntitySpec(MCDEntity mcdEntitySpec) {
        this.mcdEntitySpec = mcdEntitySpec;
    }
}
