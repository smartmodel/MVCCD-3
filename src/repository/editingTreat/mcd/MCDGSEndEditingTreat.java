package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContRelations;
import mcd.MCDGSEnd;
import mcd.MCDGeneralization;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.relation.genspec.GenSpecEditor;

import java.awt.*;

public class MCDGSEndEditingTreat extends EditingTreat {

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }


    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        MCDGeneralization generalization = ((MCDGSEnd)element).getMcdGeneralization();
        MCDContRelations contRelations = (MCDContRelations) generalization.getParent();
        return new GenSpecEditor(owner , contRelations, generalization, mode,
                new MCDGeneralizationEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }

}
