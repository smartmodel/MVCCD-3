package window.editor.mcd.relation.association;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class AssociationButtons extends PanelButtons {


    public AssociationButtons(AssociationEditor associationEditor) {
        super(associationEditor);
        //super.setButtonsContent (new AssociationButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new AssociationButtonsContent(this);
    }
}
