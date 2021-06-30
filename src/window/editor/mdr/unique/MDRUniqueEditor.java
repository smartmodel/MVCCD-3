package window.editor.mdr.unique;

import mdr.MDRContConstraints;
import mdr.MDRFK;
import mdr.MDRUnique;
import mldr.MLDRFK;
import mldr.MLDRUnique;
import mpdr.MPDRFK;
import mpdr.MPDRUnique;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MDRUniqueEditor extends DialogEditor {


    public MDRUniqueEditor(Window owner,
                           MDRContConstraints parent,
                           MDRUnique mdrUnique,
                           String mode,
                           EditingTreat editingTreat)  {
        super(owner, parent, mdrUnique, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MDRUniqueButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MDRUniqueInput(this);
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
        if ( getMvccdElementCrt() instanceof MLDRUnique){
            return "editor.mldr.unique.read";
        }
        if ( getMvccdElementCrt() instanceof MPDRUnique){
            return "editor.mpdr.unique.read";
        }
        return null;
    }


}
