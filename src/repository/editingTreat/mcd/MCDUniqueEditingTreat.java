package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContConstraints;
import mcd.MCDUnicity;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.operation.constraint.unicity.unique.UniqueEditor;
import window.editor.mcd.operation.constraint.unicity.unique.UniqueInputContent;

import java.awt.*;

public class MCDUniqueEditingTreat extends MCDTableTransitoryEditingTreat {

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new UniqueInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new UniqueEditor(owner, (MCDContConstraints) parent, (MCDUnicity) element, mode,
                new MCDUniqueEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.constraint.unique";
    }

}
