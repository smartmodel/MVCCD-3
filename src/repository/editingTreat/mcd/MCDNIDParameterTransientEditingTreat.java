package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDNID;
import mcd.MCDParameter;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.operation.parameter.ParameterEditor;
import window.editor.mcd.operation.parameter.ParameterInputContent;
import window.editor.mcd.operation.parameter.ParameterTransientEditor;

import java.awt.*;

public class MCDNIDParameterTransientEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new ParameterInputContent(element, ParameterEditor.NID);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ParameterTransientEditor(owner, (MCDNID) parent, (MCDParameter) element, mode,
                ParameterEditor.NID, new MCDNIDParameterTransientEditingTreat());

    }

    @Override
    protected String getPropertyTheElement() {
        return "the.operation.parameter";
    }


}
