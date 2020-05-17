package window.editor.attribute;

import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class AttributeTransientEditor extends AttributeEditor {



    public AttributeTransientEditor(Window owner,
                                    MCDContAttributes parent,
                                    MCDAttribute mcdAttribut,
                                    String mode,
                                    EditingTreat editingTreat)  {
        super(owner, parent, mcdAttribut, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new AttributeTransientButtons(this);
    }


}
