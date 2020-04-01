package window.editor.reserve;

import mcd.MCDRelations;
import mcd.services.MCDLink;
import newEditor.DialogEditor;
import preferences.Preferences;

import java.awt.*;

public class LinkEditor extends DialogEditor {



    public LinkEditor(Window owner,
                      MCDRelations parent,
                      MCDLink mcdLink,
                      String mode)  {
        super(owner, parent, mcdLink, mode);

        super.setSize(Preferences.LINK_WINDOW_WIDTH, Preferences.LINK_WINDOW_HEIGHT);
        super.setInput(new LinkInput(this));
        super.setButtons (new LinkButtons(this));

        super.start();
    }

    @Override
    protected String getPropertyTitleNew() {
        return "editor.link.new";
    }

    @Override
    protected String getPropertyTitleUpdate() {
        return null;
    }

}
