package window.editor.mdr.parameter;

import mdr.MDRColumn;
import mdr.MDRConstraint;
import mdr.MDRContColumns;
import mdr.MDRParameter;
import mldr.MLDRColumn;
import mldr.MLDRParameter;
import mpdr.MPDRColumn;
import mpdr.MPDRParameter;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MDRParameterEditor extends DialogEditor {


    public MDRParameterEditor(Window owner,
                              MDRConstraint parent,
                              MDRParameter mdrColumn,
                              String mode,
                              EditingTreat editingTreat)  {
        super(owner, parent, mdrColumn, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MDRParameterButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MDRParameterInput(this);
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
        if ( getMvccdElementCrt() instanceof MLDRParameter){
            return "editor.mldr.parameter.read";
        }
        if ( getMvccdElementCrt() instanceof MPDRParameter){
            return "editor.mpdr.parameter.read";
        }
        return null;
    }


}
