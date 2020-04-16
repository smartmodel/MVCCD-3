package window.editor.preferences.general;

import main.MVCCDElement;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class PrefGeneralEditor extends DialogEditor {


    //TODO-0 Il faut appuyer 2 * pour fermer la fenêtre!
    public PrefGeneralEditor(Window owner,
                             MVCCDElement parent,
                             Preferences preferences,
                             String mode)  {
        super(owner, parent, preferences, mode, DialogEditor.NOTHING);
        /*
        super.setSize(Preferences.PREFERENCES_WINDOW_WIDTH, Preferences.PREFERENCES_WINDOW_HEIGHT);
        super.setInput(new PrefGeneralInput(this));
        super.setButtons (new PrefGeneralButtons(this));

        super.start();

         */
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new PrefGeneralButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new PrefGeneralInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
         return new Dimension (Preferences.PREFERENCES_WINDOW_WIDTH, Preferences.PREFERENCES_WINDOW_HEIGHT);
    }

    @Override
    protected String getPropertyTitleNew() {
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "preferences.project.general.update";
    }




}