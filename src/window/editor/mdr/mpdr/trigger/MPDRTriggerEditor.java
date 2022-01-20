package window.editor.mdr.mpdr.trigger;

import mpdr.tapis.MPDRBoxTriggers;
import mpdr.tapis.MPDRTrigger;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MPDRTriggerEditor extends DialogEditor {


    public MPDRTriggerEditor(Window owner,
                             MPDRBoxTriggers parent,
                             MPDRTrigger mpdrTrigger,
                             String mode,
                             EditingTreat editingTreat)  {
        super(owner, parent, mpdrTrigger, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MPDRTriggerButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MPDRTriggerInput(this);
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
        return "editor.mpdr.sequence.read";
    }


    @Override
    protected String getTitleSpecialized() {
        return null;
    }



}
