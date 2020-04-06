package window.editor.relation.association;

import utilities.window.editor.PanelInput;

public class AssociationInput extends PanelInput {

    public AssociationInput(AssociationEditor associationEditor) {
        super(associationEditor);
        super.setInputContent( new AssociationInputContent(this));
    }


}
