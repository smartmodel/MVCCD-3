package repository.editingTreat.preferences;

import main.MVCCDElement;
import main.MVCCDElementApplicationPreferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.preferences.application.PrefApplicationEditor;

import java.awt.*;

public class PrefApplEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }
    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new PrefApplicationEditor(owner , null,
                ( MVCCDElementApplicationPreferences) element, DialogEditor.UPDATE,
                new PrefApplEditingTreat());

    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }
}
