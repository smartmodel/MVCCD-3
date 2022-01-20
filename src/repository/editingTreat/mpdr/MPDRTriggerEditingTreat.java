package repository.editingTreat.mpdr;

import main.MVCCDElement;
import mpdr.tapis.MPDRBoxTriggers;
import mpdr.tapis.MPDRTrigger;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.mpdr.trigger.MPDRTriggerEditor;

import java.awt.*;

public class MPDRTriggerEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MPDRTriggerEditor(owner, (MPDRBoxTriggers) parent, (MPDRTrigger) element, mode,
            new MPDRTriggerEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.trigger";
    }

}
