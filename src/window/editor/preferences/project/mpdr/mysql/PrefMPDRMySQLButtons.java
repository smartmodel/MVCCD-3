package window.editor.preferences.project.mpdr.mysql;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class PrefMPDRMySQLButtons extends PanelButtons {

    public PrefMPDRMySQLButtons(PrefMPDRMySQLEditor prefMPDRMySQLEditor) {
        super(prefMPDRMySQLEditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new PrefMPDRMySQLButtonsContent(this);
    }
}
