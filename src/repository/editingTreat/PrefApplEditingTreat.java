package repository.editingTreat;

import main.MVCCDElementApplicationPreferences;
import main.MVCCDManager;
import utilities.window.editor.DialogEditor;
import window.editor.preferences.application.PrefApplicationEditor;

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
