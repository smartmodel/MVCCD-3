package window.editor.preferences.general;

import utilities.window.editor.PanelInput;
import window.editor.preferences.general.PrefGeneralEditor;
import window.editor.preferences.general.PrefGeneralInputContent;

public class PrefGeneralInput extends PanelInput {

     public PrefGeneralInput(PrefGeneralEditor prefGeneralEditor) {
        super(prefGeneralEditor);
        super.setInputContent(new PrefGeneralInputContent(this));
    }


}
