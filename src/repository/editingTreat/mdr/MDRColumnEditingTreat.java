package repository.editingTreat.mdr;

import main.MVCCDElement;
import mdr.MDRColumn;
import mdr.MDRContColumns;
import mdr.MDRContTables;
import mdr.MDRTable;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.column.MDRColumnEditor;
import window.editor.mdr.column.MDRColumnInputContent;
import window.editor.mdr.table.MDRTableEditor;
import window.editor.mdr.table.MDRTableInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MDRColumnEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return new MDRColumnInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRColumnEditor(owner, (MDRContColumns) parent, (MDRColumn) element, mode,
                new MDRColumnEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.column";
    }

}
