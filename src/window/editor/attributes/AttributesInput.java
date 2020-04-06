package window.editor.attributes;


import utilities.window.editor.PanelInput;

public class AttributesInput extends PanelInput {

    public AttributesInput(AttributesEditor attributesEditor) {
        super(attributesEditor);
        super.setInputContent( new AttributesInputContent(this));
    }


}
