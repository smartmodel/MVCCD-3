package window.editor.preferences.project.mdr;

import main.MVCCDElement;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.preferences.project.PrefEditor;
import window.editor.preferences.project.PrefProjectMenu;

import java.awt.*;

public class PrefMDREditor extends PrefEditor {


    //TODO-0 Il faut appuyer 2 * pour fermer la fenêtre!
    public PrefMDREditor(Window owner,
                         MVCCDElement parent,
                         Preferences preferences,
                         String mode,
                         EditingTreat editingTreat)  {
        super(owner, parent, preferences, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new PrefMDRButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new PrefMDRInput(this);
    }


    @Override
    protected Object getNavNodeMenu() {
        return PrefProjectMenu.MDR;
    }

}