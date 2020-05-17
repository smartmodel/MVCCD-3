package window.editor.mcddatatype;

import datatypes.MCDDatatype;
import main.MVCCDElement;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MCDDatatypeEditor extends DialogEditor {



    public MCDDatatypeEditor(Window owner,
                             MVCCDElement parent,
                             MCDDatatype mcdDatatype,
                             String mode,
                             EditingTreat editingTreat)  {
        super(owner, parent, mcdDatatype, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

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
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "mcddatype.application.read";
    }
}
