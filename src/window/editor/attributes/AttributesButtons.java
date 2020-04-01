package window.editor.attributes;

import newEditor.PanelButtons;
import window.editor.entity.EntityButtonsContent;
import window.editor.entity.EntityEditor;

public class AttributesButtons extends PanelButtons {


    public AttributesButtons(AttributesEditor attributesEditor) {
        super(attributesEditor);
        super.setButtonsContent (new AttributesButtonsContent(this));
    }

}
