package window.editor.preferences.general;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class PrefGeneralButtons extends PanelButtons {

    public PrefGeneralButtons(PrefGeneralEditor prefGeneralEditor) {
        super(prefGeneralEditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent getButtonsContentCustom() {
        return new PrefGeneralButtonsContent(this);
    }
}
