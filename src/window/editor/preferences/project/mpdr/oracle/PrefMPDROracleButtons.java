package window.editor.preferences.project.mpdr.oracle;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class PrefMPDROracleButtons extends PanelButtons {

    public PrefMPDROracleButtons(PrefMPDROracleEditor prefMPDROracleEditor) {
        super(prefMPDROracleEditor);
        //super.setButtonsContent(new PrefGeneralButtonsContent(this));

    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new PrefMPDROracleButtonsContent(this);
    }
}
