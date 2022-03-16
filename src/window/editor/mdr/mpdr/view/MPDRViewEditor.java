package window.editor.mdr.mpdr.view;

import mpdr.tapis.MPDRContTAPIs;
import mpdr.tapis.MPDRView;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MPDRViewEditor extends DialogEditor {


    public MPDRViewEditor(Window owner,
                          MPDRContTAPIs parent,
                          MPDRView mpdrView,
                          String mode,
                          EditingTreat editingTreat)  {
        super(owner, parent, mpdrView, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MPDRViewButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MPDRViewInput(this);
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
        return "editor.mpdr.view.read";
    }


    @Override
    protected String getTitleSpecialized() {
        return null;
    }



}
