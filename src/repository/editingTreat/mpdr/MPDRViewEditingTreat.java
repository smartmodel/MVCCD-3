package repository.editingTreat.mpdr;

import main.MVCCDElement;
import mpdr.tapis.MPDRContTAPIs;
import mpdr.tapis.MPDRView;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.mpdr.view.MPDRViewEditor;

import java.awt.*;

public class MPDRViewEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MPDRViewEditor(owner, (MPDRContTAPIs) parent, (MPDRView) element, mode,
            new MPDRViewEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.view";
    }

}
