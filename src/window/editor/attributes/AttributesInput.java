package window.editor.attributes;

import utilities.window.editor.PanelInput;
import window.editor.entity.EntityEditor;
import window.editor.entity.EntityInputContent;

public class AttributesInput extends PanelInput  {

    public AttributesInput(AttributesEditor attributesEditor) {
        super(attributesEditor);
        super.setInputContent( new AttributesInputContent(this));
    }


}
