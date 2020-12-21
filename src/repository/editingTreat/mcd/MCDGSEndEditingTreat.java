package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContRelEnds;
import mcd.MCDContRelations;
import mcd.MCDGSEnd;
import mcd.MCDGeneralization;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.relation.genspec.GenSpecEditor;
import window.editor.mcd.relends.RelEndsEditorBtn;

import java.awt.*;
import java.util.ArrayList;

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


    @Override
    public ArrayList<String> treatCompliant(Window owner, MVCCDElement mvccdElement) {

        return null;
    }

    @Override
    public ArrayList<String> treatTransform(Window owner, MVCCDElement mvccdElement) {
        return null;
    }

}
