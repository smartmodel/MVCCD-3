package repository.editingTreat.mcd;

import datatypes.MCDDatatype;
import main.MVCCDElement;
import main.MVCCDWindow;
import mcd.MCDPackage;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcddatatype.MCDDatatypeEditor;

import java.awt.*;
import java.util.ArrayList;

public class MCDDatatypeEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner,
                                           MVCCDElement parent,
                                           MVCCDElement element,
                                           String mode) {
        return new MCDDatatypeEditor(owner , null,
                (MCDDatatype) element, mode, new MCDDatatypeEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }

    @Override
    public ArrayList<String> treatCompliant(MVCCDWindow mvccdWindow, MVCCDElement mvccdElement) {
        return null;
    }

}
