package window.editor.relation.association;

import utilities.window.editor.PanelButtons;

public class AssociationButtons extends PanelButtons {


    public AssociationButtons(AssociationEditor associationEditor) {
        super(associationEditor);
        super.setButtonsContent (new AssociationButtonsContent(this));
    }

}
