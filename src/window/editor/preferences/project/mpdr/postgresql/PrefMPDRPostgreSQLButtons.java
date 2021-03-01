package window.editor.preferences.project.mpdr.postgresql;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class PrefMPDRPostgreSQLButtons extends PanelButtons {

    public PrefMPDRPostgreSQLButtons(PrefMPDRPostgreSQLEditor prefMPDRPostgreSQLEditor) {
        super(prefMPDRPostgreSQLEditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new PrefMPDRPostgreSQLButtonsContent(this);
    }
}
