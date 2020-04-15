package window.editor.mcddatatype;

import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MCDDatatypeInput extends PanelInput {

    public MCDDatatypeInput(MCDDatatypeEditor mcdDatatypeEditor) {
        super(mcdDatatypeEditor);
        //super.setInputContent( new MCDDatatypeInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new MCDDatatypeInputContent(this);
    }
}
