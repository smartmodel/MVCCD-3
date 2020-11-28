package window.editor.mddatatype;

import datatypes.MDDatatype;
import main.MVCCDElement;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MDDatatypeEditor extends DialogEditor {



    public MDDatatypeEditor(Window owner,
                            MVCCDElement parent,
                            MDDatatype mdDatatype,
                            String mode,
                            EditingTreat editingTreat)  {
        super(owner, parent, mdDatatype, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MDDatatypeButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new MDDatatypeInput(this);
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
        return null;
    }

    @Override
    protected String getPropertyTitleRead() {
        return "mcddatype.application.read";
    }
}
