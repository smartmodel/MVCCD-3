package window.editor.mcddatatype;

import utilities.window.editor.PanelInput;

public class MCDDatatypeInput extends PanelInput {

    public MCDDatatypeInput(MCDDatatypeEditor mcdDatatypeEditor) {
        super(mcdDatatypeEditor);
        super.setInputContent( new MCDDatatypeInputContent(this));
    }


}
