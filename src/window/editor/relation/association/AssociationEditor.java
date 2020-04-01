package window.editor.relation.association;

import mcd.MCDAssociation;
import mcd.MCDEntities;
import mcd.MCDRelations;
import newEditor.DialogEditor;
import preferences.Preferences;
import window.editor.attribute.AttributeButtons;
import window.editor.attribute.AttributeInput;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class AssociationEditor extends DialogEditor {


    //TODO-0 Passer à la nouvelle librairie + Id auto
    public AssociationEditor(
            Window owner,
            MCDRelations mcdRelations,
            MCDAssociation mcdAssociation,
            String mode)  {
        super(owner, mcdRelations, mcdAssociation, mode);

        super.setSize(Preferences.ASSOCIATION_WINDOW_WIDTH, Preferences.ASSOCIATION_WINDOW_HEIGHT);
        super.setInput(new AssociationInput(this));
        super.setButtons (new AssociationButtons(this));

        super.start();
    }

    @Override
    protected String getPropertyTitleNew() {
        return "editor.association.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.association.update";
    }

}
