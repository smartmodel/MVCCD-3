package window.editor.model;

import m.MElement;
import main.MVCCDElement;
import mcd.MCDElement;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContainer;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class ModelEditor extends DialogEditor {

    public ModelEditor(Window owner,
                       IMCDContContainer parent,
                       IMCDContainer element,
                       String mode,
                       int scope            )  {
        super(owner, (MVCCDElement) parent, (MVCCDElement) element, mode, scope);
/*
        super.setSize(Preferences.MODEL_WINDOW_WIDTH, Preferences.MODEL_WINDOW_HEIGHT);
        super.setInput(new ModelInput(this));
        super.setButtons (new ModelButtons(this));

        super.start();

 */
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
    protected String getPropertyTitleNew() {
        if (scope == DialogEditor.MODEL) {
            return "editor.model.new";
        }
        if (scope == DialogEditor.PACKAGE) {
            return "editor.package.new";
        }

        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        if (scope == DialogEditor.MODEL) {
            return "editor.model.update";
        }
        if (scope == DialogEditor.PACKAGE) {
            return "editor.package.update";
        }
        return null;
    }
}
