package window.editor.relends;

import main.MVCCDElement;
import mcd.MCDContRelEnds;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditorNavBtn;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelNavBtn;

import java.awt.*;

public class RelEndsEditorBtn extends DialogEditorNavBtn {

    public RelEndsEditorBtn(
            Window owner,
            MVCCDElement parent,
            MCDContRelEnds mcdContRelEnds,
            String mode,
            EditingTreat editingTreat)  {
        super(owner, parent, mcdContRelEnds, mode, editingTreat);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new RelEndsButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new RelEndsInput(this);
    }

    @Override
    protected PanelNavBtn getNavCustom() {
        return new RelEndsNavBtn(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return PreferencesManager.instance().preferences().getENTITY_WINDOW_SIZE_CUSTOM();
     }

    @Override
    protected void setSizeCustom(Dimension dimension) {
        PreferencesManager.instance().preferences().setENTITY_WINDOW_SIZE_CUSTOM(getSize());
    }

    @Override
    protected Point getLocationCustom() {
        return PreferencesManager.instance().preferences().getENTITY_WINDOW_LOCATION_ONSCREEN();

    }

    @Override
    protected void setLocationCustom(Point point) {
        PreferencesManager.instance().preferences().setENTITY_WINDOW_LOCATION_ONSCREEN(getLocationOnScreen());
    }

    @Override
    protected String getPropertyTitleNew() {
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.entity.relends.update";
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.entity.relends.read";
    }

    protected String getElementNameTitle(){
        return getMvccdElementCrt().getParent().getName();
    }
}
