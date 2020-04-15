package window.editor.preferences.MCD;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class PrefMCDInput extends PanelInput {

     public PrefMCDInput(PrefMCDEditor prefMCDEditor) {
        super(prefMCDEditor);
        //super.setInputContent(new PrefMCDInputContent(this));
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new PrefMCDInputContent(this);
    }
}
