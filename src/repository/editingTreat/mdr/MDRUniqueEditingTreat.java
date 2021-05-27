package repository.editingTreat.mdr;

import main.MVCCDElement;
import mdr.MDRContConstraints;
import mdr.MDRFK;
import mdr.MDRUnique;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.fk.MDRFKEditor;
import window.editor.mdr.table.MDRTableInputContent;
import window.editor.mdr.unique.MDRUniqueEditor;

import java.awt.*;

public class MDRUniqueEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return new MDRTableInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRUniqueEditor(owner, (MDRContConstraints) parent, (MDRUnique) element, mode,
                new MDRUniqueEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.unique";
    }

}
