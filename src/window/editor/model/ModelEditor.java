package window.editor.model;

import mcd.MCDElement;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class ModelEditor extends DialogEditor {
    public static final int MODEL = 1 ;
    public static final int PACKAGE = 2 ;

    private int scope;

    public ModelEditor(Window owner,
                       MCDElement parent,
                       MCDElement mcdElement,
                       String mode,
                       int scope            )  {
        super(owner, parent, mcdElement, mode);
        this.scope = scope;
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
        if (scope == MODEL) {
            return "editor.model.new";
        }
        if (scope == PACKAGE) {
            return "editor.package.new";
        }

        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        if (scope == MODEL) {
            return "editor.model.update";
        }
        if (scope == PACKAGE) {
            return "editor.package.update";
        }
        return null;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }
}
