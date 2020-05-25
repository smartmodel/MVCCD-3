package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import mcd.services.MCDAttributeService;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import project.ProjectService;
import utilities.Debug;
import utilities.window.DialogMessage;
import utilities.window.editor.PanelInputContent;
import window.editor.attribute.AttributeEditor;
import window.editor.attribute.AttributeInputContent;
import window.editor.entity.EntityEditor;
import window.editor.entity.EntityInputContent;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;

public class MCDAttributeEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new AttributeInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new AttributeEditor(owner , (MCDContAttributes) parent, (MCDAttribute) element, mode,
                new MCDAttributeEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.attribute";
    }



    @Override
    protected ArrayList<String> checkCompliant(MVCCDElement mvccdElement) {
        ArrayList<String> resultat = new ArrayList<String>();
        return resultat;
    }
}
