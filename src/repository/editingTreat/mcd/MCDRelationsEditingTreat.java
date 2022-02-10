package repository.editingTreat.mcd;

import main.MVCCDElement;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import java.awt.*;

public class MCDRelationsEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return null;
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.container";
    }


}
