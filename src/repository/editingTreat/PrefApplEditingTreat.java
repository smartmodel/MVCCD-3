package repository.editingTreat;

import main.MVCCDElementApplicationPreferences;
import main.MVCCDManager;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.services.MCDEntityService;
import messages.MessagesBuilder;
import newEditor.DialogEditor;
import utilities.Debug;
import utilities.window.DialogMessage;
import window.editor.entity.EntityEditor;
import window.editor.preferences.application.PrefApplicationEditor;

import javax.swing.*;
import java.awt.*;

public class PrefApplEditingTreat {


    public static void treatUpdate(Window owner,
                                   MVCCDElementApplicationPreferences applPref) {
        PrefApplicationEditor fen = new PrefApplicationEditor(owner , null, applPref, DialogEditor.UPDATE);
        fen.setVisible(true);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
    }
}
