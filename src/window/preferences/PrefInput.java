package window.preferences;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;
import utilities.window.editor.PanelInput;

public class PrefInput extends PanelInput {

     public PrefInput(PrefEditor prefEditor) {
        super(prefEditor);
        PrefInputContent inputContent = new PrefInputContent(this);
        super.setContent(inputContent);
    }


}
