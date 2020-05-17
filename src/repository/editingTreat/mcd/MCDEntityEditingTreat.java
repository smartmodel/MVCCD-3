package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDAssociation;
import mcd.MCDContEntities;
import mcd.MCDContRelations;
import mcd.MCDEntity;
import mcd.services.MCDEntityService;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import utilities.Debug;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.entity.EntityEditor;
import window.editor.entity.EntityInputContent;
import window.editor.relation.association.AssociationEditor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;

public class MCDEntityEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new EntityInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new EntityEditor(owner, (MCDContEntities) parent, (MCDEntity) element, mode,
                new MCDEntityEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.entity";
    }


    @Override
    protected ArrayList<String> checkCompliant(MVCCDElement mvccdElement) {
        ArrayList<String> resultat = new ArrayList<String>();
        return resultat;
    }


}
