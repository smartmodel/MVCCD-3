package window.editor.mddatatype;

import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class MDDatatypeInput extends PanelInput {

    public MDDatatypeInput(MDDatatypeEditor MDDatatypeEditor) {
        super(MDDatatypeEditor);
        //super.setInputContent( new MCDDatatypeInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new MDDatatypeInputContent(this);
    }
}
