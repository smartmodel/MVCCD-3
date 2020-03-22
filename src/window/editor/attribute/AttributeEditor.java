package window.editor.attribute;

import main.MVCCDWindow;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;
import window.editor.entity.EntityButtons;
import window.editor.entity.EntityInput;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class AttributeEditor extends DialogEditor {



    public AttributeEditor(Window owner, DefaultMutableTreeNode node, String mode)  {
        super(owner, node, mode);

        super.setSize(Preferences.ENTITY_WINDOW_WIDTH, Preferences.ENTITY_WINDOW_HEIGHT);
        super.setInput(new AttributeInput(this));
        super.setButtons (new AttributeButtons(this));

        super.start();
    }

    @Override
    protected String getPropertyTitleNew() {
        return "editor.attribute.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.attribute.update";
    }

}
