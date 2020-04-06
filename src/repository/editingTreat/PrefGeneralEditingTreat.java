package repository.editingTreat;

import main.MVCCDManager;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import window.editor.preferences.general.PrefGeneralEditor;

import java.awt.*;

public class PrefGeneralEditingTreat {


    public static void treatUpdate(Window owner,
                                   Preferences preferences) {
        PrefGeneralEditor fen = new PrefGeneralEditor(owner , null, preferences, DialogEditor.UPDATE);
        fen.setVisible(true);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
    }
}
