package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContRelEnds;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.relends.RelEndsEditorBtn;

import java.awt.*;
import java.util.ArrayList;

public class MCDRelEndsEditingTreat extends EditingTreat {

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new RelEndsEditorBtn(owner , parent, (MCDContRelEnds)element, mode,
                new MCDRelEndsEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }


    @Override
    protected ArrayList<String> checkCompliant(MVCCDElement mvccdElement) {
        ArrayList<String> resultat = new ArrayList<String>();
        return resultat;
    }
}
