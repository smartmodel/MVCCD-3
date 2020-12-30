package repository.editingTreat.mdr;

import main.MVCCDElement;
import mdr.MDRContConstraints;
import mdr.MDRFK;
import mdr.MDRPK;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.fk.MDRFKEditor;
import window.editor.mdr.pk.MDRPKEditor;
import window.editor.mdr.table.MDRTableInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MDRFKEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return new MDRTableInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRFKEditor(owner, (MDRContConstraints) parent, (MDRFK) element, mode,
                new MDRFKEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.fk";
    }

}
