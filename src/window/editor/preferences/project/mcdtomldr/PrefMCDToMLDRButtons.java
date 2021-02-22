package window.editor.preferences.project.mcdtomldr;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class PrefMCDToMLDRButtons extends PanelButtons {

    public PrefMCDToMLDRButtons(PrefMCDToMLDREditor PrefMCDToMLDREditor) {
        super(PrefMCDToMLDREditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {

        return new PrefMCDToMLDRButtonsContent(this);
    }
}
