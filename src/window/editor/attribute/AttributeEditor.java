package window.editor.attribute;

import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class AttributeEditor extends DialogEditor {



    public AttributeEditor(Window owner,
                           MCDContAttributes parent,
                           MCDAttribute mcdAttribut,
                           String mode)  {
        super(owner, parent, mcdAttribut, mode, DialogEditor.NOTHING);

        /*
        super.setSize(Preferences.ATTRIBUTE_WINDOW_WIDTH, Preferences.ATTRIBUTE_WINDOW_HEIGHT);
        super.setInput(new AttributeInput(this));
        super.setButtons (new AttributeButtons(this));

        super.start();

         */
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
    protected String getPropertyTitleNew() {
        return "editor.attribute.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.attribute.update";
    }

}
