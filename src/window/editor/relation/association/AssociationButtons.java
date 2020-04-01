package window.editor.relation.association;

import newEditor.PanelButtons;
import window.editor.attribute.AttributeButtonsContent;
import window.editor.attribute.AttributeEditor;

public class AssociationButtons extends PanelButtons {


    public AssociationButtons(AssociationEditor associationEditor) {
        super(associationEditor);
        super.setButtonsContent (new AssociationButtonsContent(this));
    }

}
