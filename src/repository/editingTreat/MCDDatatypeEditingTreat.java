package repository.editingTreat;

import datatypes.MCDDatatype;
import utilities.window.editor.DialogEditor;
import window.editor.mcddatatype.MCDDatatypeEditor;

import java.awt.*;

public class MCDDatatypeEditingTreat {


    public static void treatRead(Window owner,
                                 MCDDatatype mcdDatatype) {
        MCDDatatypeEditor fen = new MCDDatatypeEditor(owner , null, mcdDatatype, DialogEditor.READ);
        fen.setVisible(true);
    }

}
