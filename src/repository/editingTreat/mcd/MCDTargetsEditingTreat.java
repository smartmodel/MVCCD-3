package repository.editingTreat.mcd;

import main.MVCCDElement;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.mcdtargets.MCDTargetsEditor;
import window.editor.mcd.operation.constraint.unicity.nid.NIDInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDTargetsEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new NIDInputContent(element);
    }


    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MCDTargetsEditor(owner, parent, element, mode,
                new MCDTargetsEditingTreat());

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
