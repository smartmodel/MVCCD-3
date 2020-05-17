package window.editor.attribute;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class AttributeTransientButtons extends AttributeButtons {


    public AttributeTransientButtons(AttributeEditor attributeEditor) {
        super(attributeEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new AttributeTransientButtonsContent(this);
    }
}
