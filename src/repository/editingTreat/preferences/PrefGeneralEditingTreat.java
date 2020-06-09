package repository.editingTreat.preferences;

import main.MVCCDElement;
import main.MVCCDManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelInputContent;
import window.editor.preferences.general.PrefGeneralEditor;

import java.awt.*;
import java.util.ArrayList;

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



    @Override
    protected ArrayList<String> checkCompliant(MVCCDElement mvccdElement) {
        ArrayList<String> resultat = new ArrayList<String>();
        return resultat;
    }
}
