package window.editor.mdr.mpdr.storedcode;

import mpdr.tapis.MPDRBoxStoredCode;
import mpdr.tapis.MPDRStoredCode;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MPDRStoredCodeEditor extends DialogEditor {


    public MPDRStoredCodeEditor(Window owner,
                                MPDRBoxStoredCode parent,
                                MPDRStoredCode mpdrStoredCode,
                                String mode,
                                EditingTreat editingTreat)  {
        super(owner, parent, mpdrStoredCode, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MPDRStoredCodeButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MPDRStoredCodeInput(this);
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
        return "editor.mpdr.stored.code.read";
    }


    @Override
    protected String getTitleSpecialized() {
        return null;
    }



}
