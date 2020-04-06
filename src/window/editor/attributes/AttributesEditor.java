package window.editor.attributes;

import mcd.MCDContAttributes;
import mcd.MCDEntity;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;

import java.awt.*;

public class AttributesEditor extends DialogEditor {

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
