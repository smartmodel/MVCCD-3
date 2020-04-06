package window.editor.attribute;

import utilities.window.editor.PanelInput;

public class AttributeInput extends PanelInput {

    public AttributeInput(AttributeEditor attributeEditor) {
        super(attributeEditor);
        super.setInputContent( new AttributeInputContent(this));
    }


}
