package repository.editingTreat.preferences;

import main.MVCCDElement;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.preferences.project.general.PrefGeneralEditor;

import java.awt.*;

public class PrefGeneralEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new PrefGeneralEditor(owner , null, (Preferences) element,
                mode, new PrefGeneralEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }
}
