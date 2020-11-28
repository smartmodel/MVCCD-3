package window.editor.preferences.project.mldrtompdr;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class PrefMLDRToMPDRButtons extends PanelButtons {

    public PrefMLDRToMPDRButtons(PrefMLDRToMPDREditor PrefMLDRToMPDREditor) {
        super(PrefMLDRToMPDREditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {

        return new PrefMLDRToMPDRButtonsContent(this);
    }
}
