package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContConstraints;
import mcd.MCDUnicity;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.operation.constraint.unicity.nid.NIDEditor;
import window.editor.mcd.operation.constraint.unicity.nid.NIDInputContent;

import java.awt.*;

public class MCDNIDEditingTreat extends MCDTableTransitoryEditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new NIDInputContent(element);
    }


    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new NIDEditor(owner, (MCDContConstraints) parent, (MCDUnicity) element, mode,
                new MCDNIDEditingTreat());

    }

    @Override
    protected String getPropertyTheElement() {
        return "the.constraint.nid";
    }
}
