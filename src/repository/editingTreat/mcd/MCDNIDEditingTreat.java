package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContConstraints;
import mcd.MCDUnicity;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.operation.constraint.unicity.UnicityEditor;
import window.editor.operation.constraint.unicity.UnicityInputContent;
import window.editor.operation.constraint.unicity.nid.NIDEditor;
import window.editor.operation.constraint.unicity.nid.NIDInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDNIDEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new NIDInputContent(element);
    }


    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new NIDEditor(owner, (MCDContConstraints) parent, (MCDUnicity) element, mode,
                new MCDNIDEditingTreat());

    }

    @Override
    protected String getPropertyTheElement() {
        return "the.constraint.nid";
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
