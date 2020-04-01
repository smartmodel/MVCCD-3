package window.editor.mcddatatype;

import newEditor.PanelInput;

public class MCDDatatypeInput extends PanelInput {

    public MCDDatatypeInput(MCDDatatypeEditor mcdDatatypeEditor) {
        super(mcdDatatypeEditor);
        super.setInputContent( new MCDDatatypeInputContent(this));
    }


}
