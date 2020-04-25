package window.editor.relation.association;

import m.MElement;
import mcd.MCDAssociation;
import mcd.MCDContRelations;
import mcd.MCDElement;
import utilities.window.editor.DialogEditor;
import preferences.Preferences;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class AssociationEditor extends DialogEditor {


    public AssociationEditor(
            Window owner,
            MCDContRelations mcdContRelations,
            MCDAssociation mcdAssociation,
            String mode)  {
        super(owner, mcdContRelations, mcdAssociation, mode, DialogEditor.NOTHING);

        /*
        super.setSize(Preferences.ASSOCIATION_WINDOW_WIDTH, Preferences.ASSOCIATION_WINDOW_HEIGHT);
        super.setInput(new AssociationInput(this));
        super.setButtons (new AssociationButtons(this));

        super.start();

         */
    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new AssociationButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new AssociationInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension(Preferences.ASSOCIATION_WINDOW_WIDTH, Preferences.ASSOCIATION_WINDOW_HEIGHT);
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
