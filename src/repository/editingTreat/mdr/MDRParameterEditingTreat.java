package repository.editingTreat.mdr;

import main.MVCCDElement;
import mdr.MDRConstraint;
import mdr.MDRParameter;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.parameter.MDRParameterEditor;
import window.editor.mdr.parameter.MDRParameterInputContent;

import java.awt.*;

public class MDRParameterEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return new MDRParameterInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRParameterEditor(owner, (MDRConstraint) parent, (MDRParameter) element, mode,
                new MDRParameterEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.parameter";
    }

}
