package repository.editingTreat.mdr;

import main.MVCCDElement;
import mdr.MDRContTables;
import mdr.MDRTable;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.table.MDRTableEditor;
import window.editor.mdr.table.MDRTableInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MDRTableEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return new MDRTableInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRTableEditor(owner, (MDRContTables) parent, (MDRTable) element, mode,
                new MDRTableEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.table";
    }

}
