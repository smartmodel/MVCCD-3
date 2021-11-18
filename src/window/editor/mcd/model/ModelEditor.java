package window.editor.mcd.model;

import main.MVCCDElement;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContainer;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class ModelEditor extends DialogEditor {

    public static final int MODEL = 1 ;
    public static final int PACKAGE = 2 ;


    public ModelEditor(Window owner,
                       IMCDContContainer parent,
                       IMCDContainer element,
                       String mode,
                       int scope,
                       EditingTreat editingTreat)  {
        super(owner, (MVCCDElement) parent, (MVCCDElement) element, mode, scope, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {

        return new ModelButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new ModelInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension(Preferences.MODEL_WINDOW_WIDTH, Preferences.MODEL_WINDOW_HEIGHT);
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
        if (scope == ModelEditor.MODEL) {
            return "editor.model.new";
        }
        if (scope == ModelEditor.PACKAGE) {
            return "editor.package.new";
        }

        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        if (scope == ModelEditor.MODEL) {
            return "editor.model.update";
        }
        if (scope == ModelEditor.PACKAGE) {
            return "editor.package.update";
        }
        return null;
    }

    @Override
    protected String getPropertyTitleRead() {
        if (scope == ModelEditor.MODEL) {
            return "editor.model.read";
        }
        if (scope == ModelEditor.PACKAGE) {
            return "editor.package.read";
        }
        return null;
    }

    @Override
    protected String getTitleSpecialized() {
        return null;
    }

}
