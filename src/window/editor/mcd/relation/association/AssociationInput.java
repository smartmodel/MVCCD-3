package window.editor.mcd.relation.association;

import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class AssociationInput extends PanelInput {

    public AssociationInput(AssociationEditor associationEditor) {
        super(associationEditor);
        //super.setInputContent( new AssociationInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new AssociationInputContent(this);
    }
}
