package window.editor.mdr.pk;

import mdr.MDRContConstraints;
import mdr.MDRPK;
import mldr.MLDRPK;
import mpdr.MPDRPK;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MDRPKEditor extends DialogEditor {


    public MDRPKEditor(Window owner,
                       MDRContConstraints parent,
                       MDRPK mdrPK,
                       String mode,
                       EditingTreat editingTreat)  {
        super(owner, parent, mdrPK, mode, DialogEditor.SCOPE_NOTHING, editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MDRPKButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MDRPKInput(this);
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
        if ( getMvccdElementCrt() instanceof MLDRPK){
            return "editor.mldr.pk.read";
        }
        if ( getMvccdElementCrt() instanceof MPDRPK){
            return "editor.mpdr.pk.read";
        }
        return null;
    }

    @Override
    protected String getTitleSpecialized() {
        return null;
    }


}
