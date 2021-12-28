package window.editor.preferences.project.mpdr.oracle;

import main.MVCCDElement;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.preferences.project.PrefProjectMenu;
import window.editor.preferences.project.mpdr.PrefMPDREditor;

import java.awt.*;

public class PrefMPDROracleEditor extends PrefMPDREditor {


    public PrefMPDROracleEditor(Window owner,
                                MVCCDElement parent,
                                Preferences preferences,
                                String mode,
                                EditingTreat editingTreat)  {
        super(owner, parent, preferences, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new PrefMPDROracleButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new PrefMPDROracleInput(this);
    }

    @Override
    protected Object getNavNodeMenu() {
        return PrefProjectMenu.MPDROracle;
    }

}