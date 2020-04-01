package repository.editingTreat;

import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import mcd.MCDEntities;
import mcd.MCDEntity;
import mcd.services.MCDAttributeService;
import messages.MessagesBuilder;
import newEditor.DialogEditor;
import project.ProjectService;
import utilities.Debug;
import utilities.window.DialogMessage;
import window.editor.attribute.AttributeEditor;
import window.editor.attributes.AttributesEditor;
import window.editor.entity.EntityEditor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
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
