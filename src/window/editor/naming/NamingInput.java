package window.editor.naming;

import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class NamingInput extends PanelInput {

    public NamingInput(NamingEditor NamingEditor) {
        super(NamingEditor);
        //super.setInputContent( new MCDDatatypeInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new NamingInputContent(this);
    }
}
