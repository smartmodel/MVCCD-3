package window.editor.mcd.entity.compliant;

import main.MVCCDManager;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditorNavBtn;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelNavBtn;

import java.awt.*;
import java.awt.event.WindowEvent;

public class EntCompliantEditorBtn extends DialogEditorNavBtn {

    private MCDEntity mcdEntity;

    public EntCompliantEditorBtn(Window owner,
                                 MCDContEntities parent,
                                 MCDEntity mcdEntity,
                                 String mode,
                                 EditingTreat editingTreat)  {
        super(owner, parent, mcdEntity, mode, editingTreat);
        this.mcdEntity = mcdEntity;
        MVCCDManager.instance().getConsoleManager().setiConsoleContentFrontEnd((EntCompliantButtonsContent) this.getButtons().getButtonsContent());

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new EntCompliantButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new EntCompliantInput(this);
    }

    @Override
    protected PanelNavBtn getNavCustom() {
        return new EntCompliantNavBtn(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return PreferencesManager.instance().preferences().getENTITY_WINDOW_SIZE_CUSTOM();
        //return new Dimension(Preferences.ENTITY_WINDOW_WIDTH, Preferences.ENTITY_WINDOW_HEIGHT);
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
        return "";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return null;
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.entity.compliant.read";
    }

    @Override
    protected String getTitleSpecialized() {
        return null;
    }



    @Override
    public void windowOpened(WindowEvent windowEvent) {
        super.windowOpened(windowEvent);
        ((EntCompliantInputContent) this.getInput().getInputContent()).checkCompliant(mcdEntity);
    }

}
