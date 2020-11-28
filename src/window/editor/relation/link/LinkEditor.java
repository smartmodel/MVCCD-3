package window.editor.relation.link;

import mcd.MCDContRelations;
import mcd.MCDLink;
import preferences.Preferences;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelInput;

import java.awt.*;

public class LinkEditor extends DialogEditor {


    public LinkEditor(
            Window owner,
            MCDContRelations mcdContRelations,
            MCDLink mcdLink,
            String mode,
            EditingTreat editingTreat)  {
        super(owner, mcdContRelations, mcdLink, mode, DialogEditor.SCOPE_NOTHING,
                editingTreat);

    }

    @Override
    protected PanelButtons getButtonsCustom() {
        return new LinkButtons(this);
    }

    @Override
    protected PanelInput getInputCustom() {
        return new LinkInput(this);
    }

    @Override
    protected Dimension getSizeCustom() {
        return new Dimension(Preferences.LINK_WINDOW_WIDTH, Preferences.LINK_WINDOW_HEIGHT);
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
        return "editor.link.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return "editor.link.update";
    }

    @Override
    protected String getPropertyTitleRead() {
        return "editor.link.read";
    }

}
