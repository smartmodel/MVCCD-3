package window.editor.attribute;

import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class AttributeInput extends PanelInput {

    public AttributeInput(AttributeEditor attributeEditor) {
        super(attributeEditor);
        //super.setInputContent( new AttributeInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new AttributeInputContent(this);
    }
}
