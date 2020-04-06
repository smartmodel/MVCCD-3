package window.editor.preferences.general;

import utilities.window.editor.PanelButtons;

public class PrefGeneralButtons extends PanelButtons {

    public PrefGeneralButtons(PrefGeneralEditor prefGeneralEditor) {
        super(prefGeneralEditor);
        super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


}
