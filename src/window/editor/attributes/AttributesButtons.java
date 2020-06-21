package window.editor.attributes;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class AttributesButtons extends PanelButtons {


    public AttributesButtons(AttributesEditorBtn attributesEditor) {
        super(attributesEditor);
        //super.setButtonsContent (new AttributesButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new AttributesButtonsContent(this);
    }
}
