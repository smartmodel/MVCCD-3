package window.editor.relation.association;

import newEditor.PanelInput;
import window.editor.attribute.AttributeEditor;
import window.editor.attribute.AttributeInputContent;

public class AssociationInput extends PanelInput {

    public AssociationInput(AssociationEditor associationEditor) {
        super(associationEditor);
        super.setInputContent( new AssociationInputContent(this));
    }


}
