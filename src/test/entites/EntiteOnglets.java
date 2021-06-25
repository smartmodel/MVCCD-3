package test.entites;

import mcd.MCDContAttributes;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import test.entites.boutons.PanelBoutons;
import test.entites.onglets.Onglets;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import javax.swing.*;
import java.awt.*;

public class EntiteOnglets extends DialogEditor {

    public EntiteOnglets(Window owner,
                         MCDContEntities mcdContEntities, MCDEntity mcdEntity,
                         String mode,
                         EditingTreat editingTreat){
        super(owner, mcdContEntities, mcdEntity,
                mode, DialogEditor.SCOPE_NOTHING, editingTreat);

        setSize(new Dimension(850,700));
        setLocationRelativeTo(null);
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new PanelBoutons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new Onglets(this);
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
        return "editor.entity.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.entity.update";
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.entity.read";
    }

    @Override
    public void confirmClose(){
        if (getInput().getInputContent().datasChangedNow()) {
            String message = MessagesBuilder.getMessagesProperty("editor.close.change.not.saved");
            boolean confirm = DialogMessage.showConfirmYesNo_No(this, message) == JOptionPane.YES_OPTION;
            if (confirm) {
                myDispose();
                //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//cancel
            }
        } else {
            myDispose();
        }
    }
}
