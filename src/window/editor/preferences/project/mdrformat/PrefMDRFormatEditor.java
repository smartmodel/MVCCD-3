package window.editor.preferences.project.mdrformat;

import main.MVCCDElement;
import preferences.Preferences;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.*;
import window.editor.preferences.project.PrefEditor;
import window.editor.preferences.project.PrefNavTree;
import window.editor.preferences.project.PrefProjectMenu;

import java.awt.*;

public class PrefMDRFormatEditor extends PrefEditor {


    //TODO-0 Il faut appuyer 2 * pour fermer la fenêtre!
    public PrefMDRFormatEditor(Window owner,
                               MVCCDElement parent,
                               Preferences preferences,
                               String mode,
                               EditingTreat editingTreat)  {
        super(owner, parent, preferences, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new PrefMDRFormatButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new PrefMDRFormatInput(this);
    }


    @Override
    protected Object getNavNodeMenu() {
        return PrefProjectMenu.MDRFormat;
    }

}