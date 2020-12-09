package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.*;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.operation.constraint.unicity.unique.UniqueEditor;
import window.editor.mcd.operation.constraint.unicity.unique.UniqueInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDUniqueEditingTreat extends EditingTreat {



    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new UniqueInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new UniqueEditor(owner, (MCDContConstraints) parent, (MCDUnicity) element, mode,
                new MCDUniqueEditingTreat());

    }


    @Override
    protected String getPropertyTheElement() {
        return "the.constraint.unique";
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
