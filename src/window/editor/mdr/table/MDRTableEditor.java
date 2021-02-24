package window.editor.mdr.table;

import mdr.MDRContTables;
import mdr.MDRTable;
import mldr.MLDRTable;
import mpdr.MPDRTable;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditorNavBtn;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelNavBtn;

import java.awt.*;

public class MDRTableEditor extends DialogEditorNavBtn {


    public MDRTableEditor(Window owner,
                          MDRContTables parent,
                          MDRTable mdrTable,
                          String mode,
                          EditingTreat editingTreat)  {
        super(owner, parent, mdrTable, mode, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MDRTableButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MDRTableInput(this);
    }

    @Override
    protected PanelNavBtn getNavCustom() {
        return new MDRTableNavBtn(this);
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
        if ( getMvccdElementCrt() instanceof MLDRTable){
            return "editor.mldr.table.update";
        }
        if ( getMvccdElementCrt() instanceof MPDRTable){
            return "editor.mpdr.table.update";
        }
        return null;
    }

    @Override
    protected String getPropertyTitleRead() {
        if ( getMvccdElementCrt() instanceof MLDRTable){
            return "editor.mldr.table.read";
        }
        if ( getMvccdElementCrt() instanceof MPDRTable){
            return "editor.mpdr.table.read";
        }
        return null;
    }


}
