package window.editor.mdr.fk;

import mdr.MDRContConstraints;
import mdr.MDRFK;
import mldr.MLDRFK;
import mldr.MLDRPK;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MDRFKEditor extends DialogEditor {


    public MDRFKEditor(Window owner,
                       MDRContConstraints parent,
                       MDRFK mdrFK,
                       String mode,
                       EditingTreat editingTreat)  {
        super(owner, parent, mdrFK, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MDRFKButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MDRFKInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return PreferencesManager.instance().preferences().getMDRTABLE_WINDOW_SIZE_CUSTOM();
        //return new Dimension(Preferences.MDRTABLE_WINDOW_WIDTH, Preferences.MDRTABLE_WINDOW_HEIGHT);
    }

    @Override
    protected void setSizeCustom(Dimension dimension) {
        PreferencesManager.instance().preferences().setMDRTABLE_WINDOW_SIZE_CUSTOM(getSize());
    }

    @Override
    protected Point getLocationCustom() {
        return PreferencesManager.instance().preferences().getMDRTABLE_WINDOW_LOCATION_ONSCREEN();

    }

    @Override
    protected void setLocationCustom(Point point) {
        PreferencesManager.instance().preferences().setMDRTABLE_WINDOW_LOCATION_ONSCREEN(getLocationOnScreen());
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
        if ( getMvccdElementCrt() instanceof MLDRFK){
            return "editor.mldr.fk.read";
        }
        if ( getMvccdElementCrt() instanceof MLDRPK){
            return "editor.mpdr.fk.read";
        }
        return null;
    }


}
