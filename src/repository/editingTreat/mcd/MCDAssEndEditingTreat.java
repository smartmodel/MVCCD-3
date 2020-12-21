package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.*;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.relation.association.AssociationEditor;
import window.editor.mcd.relation.genspec.GenSpecEditor;

import java.awt.*;
import java.util.ArrayList;

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


    @Override
    public ArrayList<String> treatCompliant(Window owner, MVCCDElement mvccdElement) {

        return null;
    }

    @Override
    public ArrayList<String> treatTransform(Window owner, MVCCDElement mvccdElement) {
        return null;
    }

}
