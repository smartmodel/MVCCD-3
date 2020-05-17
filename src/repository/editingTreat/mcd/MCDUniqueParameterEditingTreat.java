package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDNID;
import mcd.MCDParameter;
import mcd.MCDUnique;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.operation.parameter.ParameterEditor;
import window.editor.operation.parameter.ParameterInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDUniqueParameterEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new ParameterInputContent(element, ParameterEditor.UNIQUE);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ParameterEditor(owner, (MCDUnique) parent, (MCDParameter) element, mode,
                ParameterEditor.UNIQUE, new MCDUniqueParameterEditingTreat());

    }

    @Override
    protected String getPropertyTheElement() {
        return "the.operation.parameter";
    }


    @Override
    protected ArrayList<String> checkCompliant(MVCCDElement mvccdElement) {
        ArrayList<String> resultat = new ArrayList<String>();
        return resultat;
    }


}
