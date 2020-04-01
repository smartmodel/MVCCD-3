package window.editor.attributes;

import main.MVCCDWindow;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import mcd.MCDEntity;
import newEditor.DialogEditor;
import preferences.Preferences;
import window.editor.entity.EntityButtons;
import window.editor.entity.EntityInput;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class AttributesEditor extends DialogEditor {


    //TODO-0 Id auto
    public AttributesEditor(
            Window owner,
            MCDEntity parent,
            MCDContAttributes mcdContAttributes,
            String mode)  {
        super(owner, parent, mcdContAttributes, mode);

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
