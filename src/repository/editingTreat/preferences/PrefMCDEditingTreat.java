package repository.editingTreat.preferences;

import main.MVCCDElement;
import main.MVCCDWindow;
import mcd.MCDPackage;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelInputContent;
import window.editor.preferences.project.PrefProject;
import window.editor.preferences.project.PrefProjectMenu;
import window.editor.preferences.project.mcd.PrefMCDEditor;

import java.awt.*;
import java.util.ArrayList;

public class PrefMCDEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent,
                                           MVCCDElement element, String mode) {

        return new PrefMCDEditor(owner , null, (Preferences) element,
                mode, new PrefMCDEditingTreat());

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
