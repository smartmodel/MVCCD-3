package window.editor.preferences.project.mcd;

import main.MVCCDElement;
import preferences.Preferences;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.*;
import window.editor.preferences.project.PrefEditor;
import window.editor.preferences.project.PrefNavTree;
import window.editor.preferences.project.PrefProject;
import window.editor.preferences.project.PrefProjectMenu;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class PrefMCDEditor extends PrefEditor {


    //TODO-0 Il faut appuyer 2 * pour fermer la fenêtre!
    public PrefMCDEditor(Window owner,
                         MVCCDElement parent,
                         Preferences preferences,
                         String mode,
                         EditingTreat editingTreat)  {
        super(owner, parent, preferences, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new PrefMCDButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new PrefMCDInput(this);
    }

   @Override
    protected Object getNavNodeMenu() {
        return PrefProjectMenu.MCD;
    }

}