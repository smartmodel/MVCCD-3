package window.editor.preferences.general;

import utilities.window.editor.PanelButtons;
import window.editor.preferences.MCD.PrefMCDButtonsContent;
import window.editor.preferences.MCD.PrefMCDEditor;

public class PrefGeneralButtons extends PanelButtons {

    public PrefGeneralButtons(PrefGeneralEditor prefGeneralEditor) {
        super(prefGeneralEditor);
        super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


}
