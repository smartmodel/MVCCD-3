package window.editor.attribute;

import newEditor.PanelInput;
import window.editor.entity.EntityEditor;
import window.editor.entity.EntityInputContent;

public class AttributeInput extends PanelInput {

    public AttributeInput(AttributeEditor attributeEditor) {
        super(attributeEditor);
        super.setInputContent( new AttributeInputContent(this));
    }


}
