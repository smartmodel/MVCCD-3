package window.editor.relation.association;

import m.MElement;
import mcd.MCDAssociation;
import mcd.MCDContRelations;
import mcd.MCDElement;
import repository.editingTreat.EditingTreat;
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
            String mode,
            EditingTreat editingTreat)  {
        super(owner, mcdContRelations, mcdAssociation, mode, DialogEditor.SCOPE_NOTHING,
                editingTreat);

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
    protected void setSizeCustom(Dimension dimension) {

    }

    @Override
    protected Point getLocationCustom() {
        return null;
    }

    @Override
    protected void setLocationCustom(Point point) {

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
