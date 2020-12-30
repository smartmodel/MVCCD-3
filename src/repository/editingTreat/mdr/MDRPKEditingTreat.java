package repository.editingTreat.mdr;

import main.MVCCDElement;
import mdr.MDRContConstraints;
import mdr.MDRContTables;
import mdr.MDRPK;
import mdr.MDRTable;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.pk.MDRPKEditor;
import window.editor.mdr.table.MDRTableEditor;
import window.editor.mdr.table.MDRTableInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MDRPKEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return new MDRTableInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRPKEditor(owner, (MDRContConstraints) parent, (MDRPK) element, mode,
                new MDRPKEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.pk";
    }

}
