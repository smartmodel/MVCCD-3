package window.editor.mcddatatype;

import utilities.window.editor.PanelButtons;
import window.editor.entity.EntityButtonsContent;
import window.editor.entity.EntityEditor;

public class MCDDatatypeButtons extends PanelButtons {


    public MCDDatatypeButtons(MCDDatatypeEditor mcdDatatypeEditor) {
        super(mcdDatatypeEditor);
        super.setButtonsContent (new MCDDatatypeButtonsContent(this));
    }

}
