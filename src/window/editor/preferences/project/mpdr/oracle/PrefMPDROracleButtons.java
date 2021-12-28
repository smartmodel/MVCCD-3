package window.editor.preferences.project.mpdr.oracle;

import utilities.window.editor.PanelButtonsContent;
import window.editor.preferences.project.mpdr.PrefMPDRButtons;

public class PrefMPDROracleButtons extends PrefMPDRButtons {

    public PrefMPDROracleButtons(PrefMPDROracleEditor prefMPDROracleEditor) {
        super(prefMPDROracleEditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new PrefMPDROracleButtonsContent(this);
    }
}
