package window.editor.preferences.general;

import main.MVCCDWindow;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;

import javax.swing.tree.DefaultMutableTreeNode;

public class PrefGeneralEditor extends DialogEditor {



    public PrefGeneralEditor(MVCCDWindow mvccdWindow, DefaultMutableTreeNode node, String mode)  {
        super(mvccdWindow, node, mode);
        super.setSize(Preferences.PREFERENCES_WINDOW_WIDTH, Preferences.PREFERENCES_WINDOW_HEIGHT);
        super.setInput(new PrefGeneralInput(this));
        super.setButtons (new PrefGeneralButtons(this));

        super.start();
    }

    @Override
    protected String getPropertyTitleNew() {
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "preferences.project.general.update";
    }




}