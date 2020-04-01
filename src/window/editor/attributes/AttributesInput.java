package window.editor.attributes;


import newEditor.PanelInput;

public class AttributesInput extends PanelInput {

    public AttributesInput(AttributesEditor attributesEditor) {
        super(attributesEditor);
        super.setInputContent( new AttributesInputContent(this));
    }


}
