package repository.editingTreat.mdr;

import main.MVCCDElement;
import mdr.MDRCheck;
import mdr.MDRContConstraints;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.check.MDRCheckEditor;
import window.editor.mdr.table.MDRTableInputContent;

import java.awt.*;

public class MDRCheckEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return new MDRTableInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRCheckEditor(owner, (MDRContConstraints) parent, (MDRCheck) element, mode,
                new MDRCheckEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.check";
    }

}
