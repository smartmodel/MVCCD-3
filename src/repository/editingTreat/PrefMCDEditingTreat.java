package repository.editingTreat;

import main.MVCCDManager;
import newEditor.DialogEditor;
import preferences.Preferences;
import window.editor.preferences.MCD.PrefMCDEditor;
import window.editor.preferences.general.PrefGeneralEditor;

import java.awt.*;

public class PrefMCDEditingTreat {


    static void treatUpdate(Window owner,
                            Preferences preferences) {
        PrefMCDEditor fen = new PrefMCDEditor(owner , null, preferences, DialogEditor.UPDATE);
        fen.setVisible(true);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
    }
}
