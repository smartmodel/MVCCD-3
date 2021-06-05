package window.editor.naming;

import datatypes.MDDatatype;
import main.MVCCDElement;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.Trace;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class NamingEditor extends DialogEditor {



    public NamingEditor(Window owner,
                        MVCCDElement parent,
                        MVCCDElement mvccdElement,
                        String mode,
                        EditingTreat editingTreat)  {
        super(owner, parent, mvccdElement, mode, DialogEditor.SCOPE_NOTHING, editingTreat);
     }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new NamingButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new NamingInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension(Preferences.NAMING_WINDOW_WIDTH, Preferences.NAMING_WINDOW_HEIGHT);
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
        return "editor.naming.read";
    }
}
