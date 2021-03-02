package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDAssEnd;
import mcd.MCDAssociation;
import mcd.MCDContRelations;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.relation.association.AssociationEditor;

import java.awt.*;

public class MCDAssEndEditingTreat extends EditingTreat {

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }


    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        MCDAssociation association = ((MCDAssEnd)element).getMcdAssociation();
        MCDContRelations contRelations = (MCDContRelations) association.getParent();
        return new AssociationEditor(owner , contRelations, association, mode,
                new MCDGeneralizationEditingTreat());

    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }

}
