package window.editor.mdr.model;

import main.MVCCDElement;
import mdr.MDRModel;
import mldr.MLDRModel;
import mpdr.MPDRModel;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MDRModelEditor extends DialogEditor {


    public MDRModelEditor(Window owner,
                          MVCCDElement parent,
                          MDRModel mdrModel,
                          String mode,
                          EditingTreat editingTreat)  {
        super(owner, parent, mdrModel, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MDRModelButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MDRModelInput(this);
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
        if ( getMvccdElementCrt() instanceof MLDRModel){
            return "editor.mldr.model.update";
        }
        if ( getMvccdElementCrt() instanceof MPDRModel){
            return "editor.mpdr.model.update";
        }
        return null;   }

    @Override
    protected String getPropertyTitleRead() {
        if ( getMvccdElementCrt() instanceof MLDRModel){
            return "editor.mldr.model.read";
        }
        if ( getMvccdElementCrt() instanceof MPDRModel){
            return "editor.mpdr.model.read";
        }
        return null;
    }

    @Override
    protected String getTitleSpecialized() {
        return null;
    }

}
