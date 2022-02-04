package repository.editingTreat.mpdr;

import main.MVCCDElement;
import mpdr.tapis.MPDRBoxStoredCode;
import mpdr.tapis.MPDRStoredCode;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.mpdr.storedcode.MPDRStoredCodeEditor;

import java.awt.*;

public class MPDRStoredCodeEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MPDRStoredCodeEditor(owner, (MPDRBoxStoredCode) parent, (MPDRStoredCode) element, mode,
            new MPDRStoredCodeEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.stored.code";
    }

}
