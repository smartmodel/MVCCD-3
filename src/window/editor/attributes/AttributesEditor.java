package window.editor.attributes;

import main.MVCCDWindow;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;
import window.editor.entity.EntityButtons;
import window.editor.entity.EntityInput;

import javax.swing.tree.DefaultMutableTreeNode;

public class AttributesEditor extends DialogEditor {



    public AttributesEditor(MVCCDWindow mvccdWindow, DefaultMutableTreeNode node, String mode)  {
        super(mvccdWindow, node, mode);

        super.setSize(Preferences.ENTITY_WINDOW_WIDTH, Preferences.ENTITY_WINDOW_HEIGHT);
        super.setInput(new AttributesInput(this));
        super.setButtons (new AttributesButtons(this));

        super.start();
    }

    @Override
    protected String getPropertyTitleNew() {
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "attributes.entity.update";
    }
}
