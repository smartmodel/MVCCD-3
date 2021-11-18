package window.editor.mdr.column;

import mdr.MDRColumn;
import mdr.MDRContColumns;
import mldr.MLDRColumn;
import mpdr.MPDRColumn;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MDRColumnEditor extends DialogEditor {


    public MDRColumnEditor(Window owner,
                           MDRContColumns parent,
                           MDRColumn mdrColumn,
                           String mode,
                           EditingTreat editingTreat)  {
        super(owner, parent, mdrColumn, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MDRColumnButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MDRColumnInput(this);
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
        if ( getMvccdElementCrt() instanceof MLDRColumn){
            return "editor.mldr.column.read";
        }
        if ( getMvccdElementCrt() instanceof MPDRColumn){
            return "editor.mpdr.column.read";
        }
        return null;
    }


    @Override
    protected String getTitleSpecialized() {
        return null;
    }



}
