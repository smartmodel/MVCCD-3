package window.editor.preferences.MCD;

import main.MVCCDElement;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class PrefMCDEditor extends DialogEditor {

    public PrefMCDEditor(
            Window owner,
            MVCCDElement parent,
            Preferences preferences,
            String mode)  {
        super(owner, parent, preferences, mode);
/*
        super.setSize(Preferences.PREFERENCES_WINDOW_WIDTH, Preferences.PREFERENCES_WINDOW_HEIGHT);
        super.setInput(new PrefMCDInput(this));
        super.setButtons (new PrefMCDButtons(this));

        super.start();

 */
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new PrefMCDButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new PrefMCDInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension(Preferences.PREFERENCES_WINDOW_WIDTH, Preferences.PREFERENCES_WINDOW_HEIGHT);
    }

    @Override
    protected String getPropertyTitleNew() {
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "preferences.project.mcd.update";
    }
}