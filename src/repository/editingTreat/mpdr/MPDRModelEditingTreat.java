package repository.editingTreat.mpdr;

import main.MVCCDElement;
import mdr.MDRModel;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.model.MDRModelEditor;

import java.awt.*;

public class MPDRModelEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRModelEditor(owner, parent, (MDRModel) element, mode,
                new MPDRModelEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.model.physical";
    }

}
