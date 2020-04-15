package window.editor.mcddatatype;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MCDDatatypeButtons extends PanelButtons {


    public MCDDatatypeButtons(MCDDatatypeEditor mcdDatatypeEditor) {
        super(mcdDatatypeEditor);
        //super.setButtonsContent (new MCDDatatypeButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent getButtonsContentCustom() {
        return new MCDDatatypeButtonsContent(this);
    }
}
