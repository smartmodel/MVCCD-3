package window.editor.preferences.project.mpdr.mysql;

import main.MVCCDElement;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.preferences.project.PrefProjectMenu;
import window.editor.preferences.project.mpdr.PrefMPDREditor;

import java.awt.*;

public class PrefMPDRMySQLEditor extends PrefMPDREditor {


    public PrefMPDRMySQLEditor(Window owner,
                               MVCCDElement parent,
                               Preferences preferences,
                               String mode,
                               EditingTreat editingTreat)  {
        super(owner, parent, preferences, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new PrefMPDRMySQLButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new PrefMPDRMySQLInput(this);
    }

    @Override
    protected Object getNavNodeMenu() {
        return PrefProjectMenu.MPDRMySQL;
    }

}