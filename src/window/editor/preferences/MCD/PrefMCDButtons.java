package window.editor.preferences.MCD;

import utilities.window.editor.PanelButtons;

public class PrefMCDButtons extends PanelButtons {

    public PrefMCDButtons(PrefMCDEditor prefMCDEditor) {
        super(prefMCDEditor);
        super.setButtonsContent(new PrefMCDButtonsContent(this));

    }


}
