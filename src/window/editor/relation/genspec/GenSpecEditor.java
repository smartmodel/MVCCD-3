package window.editor.relation.genspec;

import mcd.MCDAssociation;
import mcd.MCDContRelations;
import mcd.MCDGeneralization;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class GenSpecEditor extends DialogEditor {


    public GenSpecEditor(
            Window owner,
            MCDContRelations mcdContRelations,
            MCDGeneralization mcdAsmcdGeneralization,
            String mode,
            EditingTreat editingTreat)  {
        super(owner, mcdContRelations, mcdAsmcdGeneralization, mode, DialogEditor.SCOPE_NOTHING,
                editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new GenSpecButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new GenSpecInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension(Preferences.GENSPEC_WINDOW_WIDTH, Preferences.GENSPEC_WINDOW_HEIGHT);
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
        return "editor.genspec.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.genspec.update";
    }

}
