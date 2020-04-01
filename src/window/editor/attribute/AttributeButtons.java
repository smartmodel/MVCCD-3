package window.editor.attribute;

import newEditor.PanelButtons;
import window.editor.entity.EntityButtonsContent;
import window.editor.entity.EntityEditor;

import javax.management.Attribute;

public class AttributeButtons extends PanelButtons {


    public AttributeButtons(AttributeEditor attributeEditor) {
        super(attributeEditor);
        super.setButtonsContent (new AttributeButtonsContent(this));
    }

}
