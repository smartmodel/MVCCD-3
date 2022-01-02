package repository.editingTreat.mpdr;

import main.MVCCDElement;
import mpdr.MPDRColumn;
import mpdr.MPDRSequence;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.mpdr.sequence.MPDRSequenceEditor;

import java.awt.*;

public class MPDRSequenceEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MPDRSequenceEditor(owner, (MPDRColumn) parent, (MPDRSequence) element, mode,
            new MPDRSequenceEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.sequence";
    }

}
