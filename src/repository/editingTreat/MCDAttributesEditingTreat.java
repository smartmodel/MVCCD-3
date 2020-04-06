package repository.editingTreat;

import main.MVCCDManager;
import mcd.MCDContAttributes;
import mcd.MCDEntity;
import utilities.window.editor.DialogEditor;
import window.editor.attributes.AttributesEditor;

import java.awt.*;

public class MCDAttributesEditingTreat {






    public static void treatEdit(Window owner,
                                   MCDContAttributes element) {

        AttributesEditor fen = new AttributesEditor(owner , (MCDEntity) element.getParent(),
                element, DialogEditor.READ);
        fen.setVisible(true);
        if (fen.isDatasChanged()){
            MVCCDManager.instance().setDatasProjectChanged(true);
        }
    }




}
