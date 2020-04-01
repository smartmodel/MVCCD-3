package window.editor.preferences.MCD;

import main.MVCCDElement;
import main.MVCCDElementApplicationPreferences;
import main.MVCCDWindow;
import newEditor.DialogEditor;
import preferences.Preferences;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class PrefMCDEditor extends DialogEditor {

    public PrefMCDEditor(
            Window owner,
            MVCCDElement parent,
            Preferences preferences,
            String mode)  {
        super(owner, parent, preferences, mode);

        super.setSize(Preferences.PREFERENCES_WINDOW_WIDTH, Preferences.PREFERENCES_WINDOW_HEIGHT);
        super.setInput(new PrefMCDInput(this));
        super.setButtons (new PrefMCDButtons(this));

        super.start();
    }

    @Override
    protected String getPropertyTitleNew() {
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "preferences.project.mcd.update";
    }
}