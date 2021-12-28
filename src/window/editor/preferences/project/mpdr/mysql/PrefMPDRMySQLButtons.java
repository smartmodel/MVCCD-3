package window.editor.preferences.project.mpdr.mysql;

import utilities.window.editor.PanelButtonsContent;
import window.editor.preferences.project.mpdr.PrefMPDRButtons;

public class PrefMPDRMySQLButtons extends PrefMPDRButtons {

    public PrefMPDRMySQLButtons(PrefMPDRMySQLEditor prefMPDRMySQLEditor) {
        super(prefMPDRMySQLEditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new PrefMPDRMySQLButtonsContent(this);
    }
}
