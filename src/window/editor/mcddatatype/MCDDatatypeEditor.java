package window.editor.mcddatatype;

import datatypes.MCDDatatype;
import main.MVCCDElement;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MCDDatatypeEditor extends DialogEditor {



    public MCDDatatypeEditor(Window owner,
                             MVCCDElement parent,
                             MCDDatatype mcdDatatype,
                             String mode)  {
        super(owner, parent, mcdDatatype, mode);
/*
        super.setSize(Preferences.MCDDATATYPE_WINDOW_WIDTH, Preferences.MCDDATATYPE_WINDOW_HEIGHT);
        super.setInput(new MCDDatatypeInput(this));
        super.setButtons (new MCDDatatypeButtons(this));

        super.start();

 */
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MCDDatatypeButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new MCDDatatypeInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension(Preferences.MCDDATATYPE_WINDOW_WIDTH, Preferences.MCDDATATYPE_WINDOW_HEIGHT);
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
