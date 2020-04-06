package window.editor.attribute;

import utilities.window.editor.PanelButtons;

public class AttributeButtons extends PanelButtons {


    public AttributeButtons(AttributeEditor attributeEditor) {
        super(attributeEditor);
        super.setButtonsContent (new AttributeButtonsContent(this));
    }

}
