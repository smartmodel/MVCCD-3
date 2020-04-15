package window.editor.relation.association;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class AssociationButtons extends PanelButtons {


    public AssociationButtons(AssociationEditor associationEditor) {
        super(associationEditor);
        //super.setButtonsContent (new AssociationButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent getButtonsContentCustom() {
        return new AssociationButtonsContent(this);
    }
}
