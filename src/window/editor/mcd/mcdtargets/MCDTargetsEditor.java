package window.editor.mcd.mcdtargets;

import main.MVCCDElement;
import mcd.*;
import messages.MessagesBuilder;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class MCDTargetsEditor extends DialogEditor {

     public MCDTargetsEditor(
            Window owner,
            MVCCDElement parent,
            MVCCDElement element,
            String mode,
            EditingTreat editingTreat)  {
        super(owner, parent, element, mode, DialogEditor.SCOPE_NOTHING, editingTreat);
/*
        super.setSize(Preferences.ENTITY_WINDOW_WIDTH, Preferences.ENTITY_WINDOW_HEIGHT);
        super.setInput(new AttributesInput(this));
        super.setButtons (new AttributesButtons(this));

        super.start();

 */
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new MCDTargetsButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new MCDTargetsInput(this);
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
        return null;
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.imcdelementwithtargets.targets.read";
    }

    protected String getElementNameTitle(){
         String theElement = "";
         if (getMvccdElementCrt() instanceof MCDEntity){
            theElement = MessagesBuilder.getMessagesProperty("of.entity");
         }
         if (getMvccdElementCrt() instanceof MCDAttribute){
            theElement = MessagesBuilder.getMessagesProperty("of.attribute");
        }
        if (getMvccdElementCrt() instanceof MCDOperation){
            theElement = MessagesBuilder.getMessagesProperty("of.attribute");
        }
        if (getMvccdElementCrt() instanceof MCDRelation){
            theElement = MessagesBuilder.getMessagesProperty("of.relation");
        }
        if (getMvccdElementCrt() instanceof MCDRelEnd){
            theElement = MessagesBuilder.getMessagesProperty("of.relend");
        }
        return theElement + " " +((MCDElement)getMvccdElementCrt()).getNamePathSourceDefault();
    }
}
