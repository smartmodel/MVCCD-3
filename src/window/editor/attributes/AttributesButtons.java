package window.editor.attributes;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class AttributesButtons extends PanelButtons {


    public AttributesButtons(AttributesEditor attributesEditor) {
        super(attributesEditor);
        //super.setButtonsContent (new AttributesButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent getButtonsContentCustom() {
        return new AttributesButtonsContent(this);
    }
}
