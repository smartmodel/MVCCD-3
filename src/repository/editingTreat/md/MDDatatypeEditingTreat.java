package repository.editingTreat.md;

import datatypes.MDDatatype;
import main.MVCCDElement;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mddatatype.MDDatatypeEditor;

import java.awt.*;
import java.util.ArrayList;

public class MDDatatypeEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner,
                                           MVCCDElement parent,
                                           MVCCDElement element,
                                           String mode) {
        return new MDDatatypeEditor(owner , null,
                (MDDatatype) element, mode, new MDDatatypeEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }

}
