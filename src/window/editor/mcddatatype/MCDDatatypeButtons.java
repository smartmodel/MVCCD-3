package window.editor.mcddatatype;

import newEditor.PanelButtons;

public class MCDDatatypeButtons extends PanelButtons {


    public MCDDatatypeButtons(MCDDatatypeEditor mcdDatatypeEditor) {
        super(mcdDatatypeEditor);
        super.setButtonsContent (new MCDDatatypeButtonsContent(this));
    }

}
