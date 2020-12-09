package repository.editingTreat.preferences;

import main.MVCCDElement;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.preferences.project.mldr.PrefMLDREditor;
import window.editor.preferences.project.mpdr.oracle.PrefMPDROracleEditor;

import java.awt.*;
import java.util.ArrayList;

public class PrefMPDROracleEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new PrefMPDROracleEditor(owner , null, (Preferences) element,
                mode, new PrefMPDROracleEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }


    @Override
    public ArrayList<String> treatCompliant(Window owner, MVCCDElement mvccdElement) {
        return null;
    }

    @Override
    public ArrayList<String> treatTransform(Window owner, MVCCDElement mvccdElement) {
        return null;
    }

}
