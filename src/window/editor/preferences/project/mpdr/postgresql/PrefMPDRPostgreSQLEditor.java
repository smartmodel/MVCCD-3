package window.editor.preferences.project.mpdr.postgresql;

import main.MVCCDElement;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.preferences.project.PrefProjectMenu;
import window.editor.preferences.project.mpdr.PrefMPDREditor;

import java.awt.*;

public class PrefMPDRPostgreSQLEditor extends PrefMPDREditor {


    public PrefMPDRPostgreSQLEditor(Window owner,
                                    MVCCDElement parent,
                                    Preferences preferences,
                                    String mode,
                                    EditingTreat editingTreat)  {
        super(owner, parent, preferences, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new PrefMPDRPostgreSQLButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new PrefMPDRPostgreSQLInput(this);
    }

    @Override
    protected Object getNavNodeMenu() {
        return PrefProjectMenu.MPDRPostgreSQL;
    }

}