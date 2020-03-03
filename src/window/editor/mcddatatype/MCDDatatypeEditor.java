package window.editor.mcddatatype;

import main.MVCCDWindow;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;
import window.editor.entity.EntityButtons;
import window.editor.entity.EntityInput;

import javax.swing.tree.DefaultMutableTreeNode;

public class MCDDatatypeEditor extends DialogEditor {



    public MCDDatatypeEditor(MVCCDWindow mvccdWindow, DefaultMutableTreeNode node, String mode)  {
        super(mvccdWindow, node, mode);

        super.setSize(Preferences.MCDDATATYPE_WINDOW_WIDTH, Preferences.MCDDATATYPE_WINDOW_HEIGHT);
        super.setInput(new MCDDatatypeInput(this));
        super.setButtons (new MCDDatatypeButtons(this));

        super.start();
    }

    @Override
    protected String getPropertyTitleNew() {
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "mcddatype.application.read";
    }
}
