package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContRelations;
import mcd.MCDLink;
import mcd.MCDLinkEnd;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.relation.link.LinkEditor;

import java.awt.*;

public class MCDLinkEndEditingTreat extends EditingTreat {

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }


    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        MCDLink link = ((MCDLinkEnd)element).getMcdLink();
        MCDContRelations contRelations = (MCDContRelations) link.getParent();
        return new LinkEditor(owner , contRelations, link, mode,
                new MCDGeneralizationEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }

}
