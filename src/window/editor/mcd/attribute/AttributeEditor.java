package window.editor.mcd.attribute;

import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class AttributeEditor extends DialogEditor {



    public AttributeEditor(Window owner,
                           MCDContAttributes parent,
                           MCDAttribute mcdAttribut,
                           String mode,
                           EditingTreat editingTreat)  {
        super(owner, parent, mcdAttribut, mode, DialogEditor.SCOPE_NOTHING, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new AttributeButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new AttributeInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension(Preferences.ATTRIBUTE_WINDOW_WIDTH, Preferences.ATTRIBUTE_WINDOW_HEIGHT);
    }

    @Override
    protected void setSizeCustom(Dimension dimension) {

    }

    @Override
    protected Point getLocationCustom() {
        return null;
    }

    @Override
    protected void setLocationCustom(Point point) {

    }

    @Override
    protected String getPropertyTitleNew() {
        return "editor.attribute.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.attribute.update";
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.attribute.read";
    }

    @Override
    protected String getTitleSpecialized() {
        return null;
    }


}
