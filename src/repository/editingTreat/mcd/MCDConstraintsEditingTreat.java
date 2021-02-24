package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContConstraints;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.operation.constraint.constraints.ConstraintsEditorBtn;

import java.awt.*;

public class MCDConstraintsEditingTreat extends EditingTreat {

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ConstraintsEditorBtn(owner , parent, (MCDContConstraints)element, mode,
                new MCDConstraintsEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }
}
