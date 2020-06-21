package window.editor.attributes;

import main.MVCCDElement;
import mcd.MCDContAttributes;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.*;

import java.awt.*;

public class AttributesEditorBtn extends DialogEditorNavBtn {

     public AttributesEditorBtn(
            Window owner,
            MVCCDElement parent,
            MCDContAttributes mcdContAttributes,
            String mode,
            EditingTreat editingTreat)  {
        super(owner, parent, mcdContAttributes, mode, editingTreat);
/*
        super.setSize(Preferences.ENTITY_WINDOW_WIDTH, Preferences.ENTITY_WINDOW_HEIGHT);
        super.setInput(new AttributesInput(this));
        super.setButtons (new AttributesButtons(this));

        super.start();

 */
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new AttributesButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new AttributesInput(this);
    }

    @Override
    protected PanelNavBtn getNavCustom() {
        return new AttributesNavBtn(this);
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
        return "editor.entity.attributes.update";
    }

    protected String getElementNameTitle(){
        return getMvccdElementCrt().getParent().getName();
    }
}
