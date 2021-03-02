package repository.editingTreat.preferences;

import main.MVCCDElement;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.preferences.project.mpdr.postgresql.PrefMPDRPostgreSQLEditor;

import java.awt.*;

public class PrefMPDRPostgreSQLEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new PrefMPDRPostgreSQLEditor(owner , null, (Preferences) element,
                mode, new PrefMPDRPostgreSQLEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }

}
