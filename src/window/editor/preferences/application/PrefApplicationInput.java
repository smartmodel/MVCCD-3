package window.editor.preferences.application;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class PrefApplicationInput extends PanelInput {

     public PrefApplicationInput(PrefApplicationEditor prefApplicationEditor) {
        super(prefApplicationEditor);
        //super.setInputContent(new PrefApplicationInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefApplicationInputContent(this);
    }
}
