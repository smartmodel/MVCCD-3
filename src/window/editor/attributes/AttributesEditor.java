package window.editor.attributes;

import main.MVCCDElement;
import mcd.MCDContAttributes;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.*;
import preferences.Preferences;

import java.awt.*;

public class AttributesEditor extends DialogEditorNav {

     public AttributesEditor(
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
    protected PanelNav getNavCustom() {
        return new AttributesNav(this);
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
        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "attributes.entity.update";
    }

    protected String getElementNameTitle(){
        return getMvccdElementCrt().getParent().getName();
    }
}
