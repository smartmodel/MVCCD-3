package window.editor.preferences.MCD;

import utilities.window.editor.PanelInput;

public class PrefMCDInput extends PanelInput {

     public PrefMCDInput(PrefMCDEditor prefMCDEditor) {
        super(prefMCDEditor);
        super.setInputContent(new PrefMCDInputContent(this));
    }


}
