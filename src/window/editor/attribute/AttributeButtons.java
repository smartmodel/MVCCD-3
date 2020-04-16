package window.editor.attribute;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class AttributeButtons extends PanelButtons {


    public AttributeButtons(AttributeEditor attributeEditor) {
        super(attributeEditor);
        //super.setButtonsContent (new AttributeButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new AttributeButtonsContent(this);
    }
}
