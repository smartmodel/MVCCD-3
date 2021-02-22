package window.editor.preferences.project.mdr;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class PrefMDRButtons extends PanelButtons {

    public PrefMDRButtons(PrefMDREditor prefMDREditor) {
        super(prefMDREditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new PrefMDRButtonsContent(this);
    }
}
