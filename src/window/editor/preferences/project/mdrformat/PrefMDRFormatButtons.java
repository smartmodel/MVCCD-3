package window.editor.preferences.project.mdrformat;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class PrefMDRFormatButtons extends PanelButtons {

    public PrefMDRFormatButtons(PrefMDRFormatEditor prefMDRFormatEditor) {
        super(prefMDRFormatEditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new PrefMDRFormatButtonsContent(this);
    }
}
