package window.editor.preferences.application;

import main.MVCCDManager;
import main.MVCCDWindow;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;

public class PrefApplicationEditor extends DialogEditor  {

    public PrefApplicationEditor(MVCCDWindow mvccdWindow, DefaultMutableTreeNode node, String mode)  {
        super(mvccdWindow, node, mode);

        super.setSize(Preferences.PREFERENCES_WINDOW_WIDTH, Preferences.PREFERENCES_WINDOW_HEIGHT);
        super.setInput(new PrefApplicationInput(this));
        super.setButtons (new PrefApplicationButtons(this));

        super.start();
    }

    @Override
    protected String getPropertyTitleNew() {
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "preferences.application.update";
    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
        MVCCDManager.instance().setDatasProjectEdited(false);
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }


}