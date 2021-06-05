package repository.editingTreat.naming;

import main.MVCCDElement;
import mdr.MDRColumn;
import mdr.MDRContColumns;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.column.MDRColumnEditor;
import window.editor.mdr.column.MDRColumnInputContent;
import window.editor.naming.NamingEditor;
import window.editor.naming.NamingInputContent;

import java.awt.*;

public class NamingEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new NamingEditor(owner, parent, element, mode,
                new NamingEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.element";
    }

    public DialogEditor treatNaming(Window owner, MVCCDElement element) {
        DialogEditor fen = getDialogEditor(owner, element.getParent(), element, DialogEditor.READ);
        fen.setVisible(true);
        return fen;
    }


}
