package window.editor.preferences.project.mpdr.postgresql;

import utilities.window.editor.PanelButtonsContent;
import window.editor.preferences.project.mpdr.PrefMPDRButtons;

public class PrefMPDRPostgreSQLButtons extends PrefMPDRButtons {

    public PrefMPDRPostgreSQLButtons(PrefMPDRPostgreSQLEditor prefMPDRPostgreSQLEditor) {
        super(prefMPDRPostgreSQLEditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new PrefMPDRPostgreSQLButtonsContent(this);
    }
}
