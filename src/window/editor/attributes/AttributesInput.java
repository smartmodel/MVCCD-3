package window.editor.attributes;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class AttributesInput extends PanelInput {

    public AttributesInput(AttributesEditor attributesEditor) {
        super(attributesEditor);
        //super.setInputContent( new AttributesInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new AttributesInputContent(this);
    }
}
