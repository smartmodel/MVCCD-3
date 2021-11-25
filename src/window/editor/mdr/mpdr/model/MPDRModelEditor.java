package window.editor.mdr.mpdr.model;

import connections.ConDB;
import main.MVCCDElement;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;
import window.editor.mdr.model.MDRModelEditor;

import java.awt.*;

public class MPDRModelEditor extends MDRModelEditor {
    public MPDRModelEditor(Window owner, MVCCDElement parent, MPDRModel mpdrModel, String mode, EditingTreat editingTreat) {
        super(owner, parent, mpdrModel, mode, editingTreat);
    }


    @Override
    protected PanelButtons getButtonsCustom() {
        return new MPDRModelButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {

        return new MPDRModelInput(this);
    }



    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.mpdr.model.update";
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.mpdr.model.read";
    }

    public ConDB getConDB(){
        if (getMode().equals(DialogEditor.NEW)) {
            return null;  // Pas de mode NEW
        } else {
            MPDRDB mpdrDb = ((MPDRModel)getMvccdElementCrt()).getDb();
            return ConDB.findByName(mpdrDb.getName());
            // Pas de cas de contrôle de données avec elementForCheckInput
        }
    }
}
