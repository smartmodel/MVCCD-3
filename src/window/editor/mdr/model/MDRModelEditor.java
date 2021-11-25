package window.editor.mdr.model;

import main.MVCCDElement;
import mdr.MDRModel;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;

import java.awt.*;

public  abstract class MDRModelEditor extends DialogEditor {


    public MDRModelEditor(Window owner,
                          MVCCDElement parent,
                          MDRModel mdrModel,
                          String mode,
                          EditingTreat editingTreat)  {
        super(owner, parent, mdrModel, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

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
    protected String getTitleSpecialized() {
        return null;
    }

}
