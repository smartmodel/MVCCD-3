package repository.editingTreat;

import datatypes.MCDDatatype;
import main.MVCCDElementApplicationPreferences;
import main.MVCCDManager;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.services.MCDEntityService;
import messages.MessagesBuilder;
import newEditor.DialogEditor;
import utilities.Debug;
import utilities.window.DialogMessage;
import window.editor.mcddatatype.MCDDatatypeEditor;

import javax.swing.*;
import java.awt.*;

public class MCDDatatypeEditingTreat {


    public static void treatRead(Window owner,
                                 MCDDatatype mcdDatatype) {
        MCDDatatypeEditor fen = new MCDDatatypeEditor(owner , null, mcdDatatype, DialogEditor.READ);
        fen.setVisible(true);
    }

}
