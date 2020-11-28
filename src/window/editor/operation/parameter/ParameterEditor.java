package window.editor.operation.parameter;

import mcd.MCDOperation;
import mcd.MCDParameter;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class ParameterEditor extends DialogEditor {

    public static final int UNIQUE = 1 ;
    public static final int NID = 2 ;


    public ParameterEditor(Window owner,
                           MCDOperation mcdOperation,
                           MCDParameter mcdParameter,
                           String mode,
                           int scope,
                           EditingTreat editingTreat)  {
        super(owner, mcdOperation, mcdParameter, mode, scope, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new ParameterButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new ParameterInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension (Preferences.PARAMETER_WINDOW_WIDTH, Preferences.PARAMETER_WINDOW_HEIGHT);
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
    public void adjustTitle() {

    }


    @Override
    protected String getPropertyTitleNew() {
        return "editor.parameter.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.parameter.update";
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.parameter.read";
    }

}
