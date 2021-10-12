package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDParameter;
import mcd.MCDUnique;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.operation.parameter.ParameterEditor;
import window.editor.mcd.operation.parameter.ParameterInputContent;
import window.editor.mcd.operation.parameter.ParameterTransientEditor;

import java.awt.*;

public class MCDUniqueParameterTransientEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new ParameterInputContent(element, ParameterEditor.UNIQUE);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ParameterEditor(owner, (MCDUnique) parent, (MCDParameter) element, mode,
                ParameterTransientEditor.UNIQUE, new MCDUniqueParameterTransientEditingTreat());

    }

    @Override
    protected String getPropertyTheElement() {
        return "the.operation.parameter";
    }

}
