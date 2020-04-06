package window.editor.attributes;

import utilities.window.editor.PanelButtons;

public class AttributesButtons extends PanelButtons {


    public AttributesButtons(AttributesEditor attributesEditor) {
        super(attributesEditor);
        super.setButtonsContent (new AttributesButtonsContent(this));
    }

}
