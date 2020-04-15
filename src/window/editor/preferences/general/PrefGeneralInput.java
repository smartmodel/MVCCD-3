package window.editor.preferences.general;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class PrefGeneralInput extends PanelInput {

     public PrefGeneralInput(PrefGeneralEditor prefGeneralEditor) {
        super(prefGeneralEditor);
        //super.setInputContent(new PrefGeneralInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefGeneralInputContent(this);
    }
}
